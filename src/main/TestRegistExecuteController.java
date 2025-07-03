package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration; // Enumerationをインポート
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.School;
import Bean.Student; // Student Beanをインポート
import Bean.Subject; // Subject Beanをインポート
import Bean.Test; // Test Beanをインポート
import dao.StudentDao; // StudentDaoをインポート
import dao.SubjectDao; // SubjectDaoをインポート
import dao.TestListSubjectDao; // TestListSubjectDaoをインポート

@WebServlet("/RegisterGradesExecute.action") // ★これがないと404エラーになります★
public class TestRegistExecuteController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String schoolCd = "oom"; // TestListSubjectExecuteControllerと合わせる
        School currentSchool = new School();
        currentSchool.setCd(schoolCd);

        String message = ""; // ユーザーへのメッセージ

        TestListSubjectDao testDao = new TestListSubjectDao(); // TestListSubjectDaoをインスタンス化
        StudentDao studentDao = new StudentDao(); // 学生情報を取得するために使用
        SubjectDao subjectDao = new SubjectDao(); // 科目情報を取得するために使用

        try {
            // GRMU001.jsp から送信される隠しフィールドから検索条件を取得
            // これらは、成績登録後の再検索や、エラー時の画面復元に使う可能性があるため、取得しておく
            String selectedEntYearStr = request.getParameter("selectedEntYear");
            String selectedClassNum = request.getParameter("selectedClassNum");
            String selectedSubjectCd = request.getParameter("selectedSubjectCd");
            String selectedNumStr = request.getParameter("selectedNum");

            int entYear = (selectedEntYearStr != null && !selectedEntYearStr.isEmpty()) ? Integer.parseInt(selectedEntYearStr) : 0;
            int num = (selectedNumStr != null && !selectedNumStr.isEmpty()) ? Integer.parseInt(selectedNumStr) : 0;

            // 必須パラメータのチェック
            if (entYear == 0 || selectedClassNum == null || selectedClassNum.isEmpty() || selectedSubjectCd == null || selectedSubjectCd.isEmpty() || num == 0) {
                message = "登録に必要な情報が不足しています。";
                request.setAttribute("message", message);
                // 不足している場合はGRMU001.jspに戻る
                // 再度GRMU001.jspにフォワードする際に、選択された値をリクエスト属性にセットし直す
                request.setAttribute("selectedEntYear", entYear);
                request.setAttribute("selectedClassNum", selectedClassNum);
                request.setAttribute("selectedSubjectCd", selectedSubjectCd);
                request.setAttribute("selectedNum", num);
                request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
                return;
            }

            Subject selectedSubject = subjectDao.get(selectedSubjectCd, currentSchool);
            if (selectedSubject == null) {
                message = "指定された科目が存在しません。";
                request.setAttribute("message", message);
                // 再度GRMU001.jspにフォワードする際に、選択された値をリクエスト属性にセットし直す
                request.setAttribute("selectedEntYear", entYear);
                request.setAttribute("selectedClassNum", selectedClassNum);
                request.setAttribute("selectedSubjectCd", selectedSubjectCd);
                request.setAttribute("selectedNum", num);
                request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
                return;
            }

            boolean allUpdatesSuccessful = true;
            List<String> failedStudents = new ArrayList<>();

            // リクエストパラメータから学生番号と点数を取得
            // "score_学生番号" の形式で送られてくるため、それを解析
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                if (paramName.startsWith("score_")) {
                    String studentNo = paramName.substring("score_".length());
                    String pointStr = request.getParameter(paramName);

                    // 点数が空文字列の場合は登録対象外とみなす（または0点として扱うなど、要件による）
                    if (pointStr == null || pointStr.trim().isEmpty()) {
                        continue;
                    }

                    int point = -1; // 初期値として無効な点数
                    try {
                        point = Integer.parseInt(pointStr);
                        // 点数の範囲チェック (0～100)
                        if (point < 0 || point > 100) {
                            message = "点数は0から100の範囲で入力してください。";
                            allUpdatesSuccessful = false;
                            failedStudents.add(studentNo + " (点数範囲外)");
                            continue; // この学生の処理はスキップして次へ
                        }
                    } catch (NumberFormatException e) {
                        // 数値変換エラー
                        message = "点数が無効な形式です。";
                        allUpdatesSuccessful = false;
                        failedStudents.add(studentNo + " (無効な形式)");
                        continue; // この学生の処理はスキップして次へ
                    }

                    // Testオブジェクトを作成
                    Test test = new Test();
                    // 学生情報を取得してセット (StudentDaoが必要)
                    Student student = studentDao.get(studentNo);
                    if (student == null) {
                        System.err.println("学生番号 " + studentNo + " の学生が見つかりません。");
                        failedStudents.add(studentNo);
                        allUpdatesSuccessful = false; // 学生が見つからない場合も失敗とマーク
                        continue;
                    }
                    test.setStudent(student);
                    test.setClassNum(selectedClassNum); // クラス番号は隠しフィールドから取得した値
                    test.setSubject(selectedSubject); // 科目オブジェクトは上で取得したものをセット
                    test.setSchool(currentSchool);
                    test.setNo(num);
                    test.setPoint(point);

                    // DAOを呼び出して保存 (既存なら更新、新規なら挿入)
                    try {
                        boolean saveResult = testDao.save(test);
                        if (!saveResult) {
                            allUpdatesSuccessful = false;
                            failedStudents.add(studentNo + " (DB更新失敗)");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        allUpdatesSuccessful = false;
                        failedStudents.add(studentNo + " (DBエラー)");
                        // データベースエラーが発生した場合、メッセージを詳細化
                        message = "データベース更新中にエラーが発生しました: " + e.getMessage();
                        // ここでbreakすると他の学生の更新が中断されるため、要件に応じてcontinueに変更する
                        // 今回は続行できるようにcontinueにしました
                        continue;
                    }
                }
            }

            if (allUpdatesSuccessful) {
                message = "登録が完了しました";
            } else {
                if (message.isEmpty()) { // 特定のエラーメッセージが設定されていなければ一般的な失敗メッセージ
                     message = "登録に一部失敗しました。"; // 一部成功の可能性も考慮
                }
                if (!failedStudents.isEmpty()) {
                    message += " (失敗した学生番号: " + String.join(", ", failedStudents) + ")";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "システムエラーが発生しました：" + e.getMessage();
        }

        // 登録完了メッセージをリクエスト属性に設定
        request.setAttribute("message", message);

        // ★★★ 目的の完了画面である GRMU002.jsp にフォワードする ★★★
        request.getRequestDispatcher("log/GRMU002.jsp").forward(request, response);
    }
}