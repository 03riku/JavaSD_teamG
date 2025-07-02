package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.School;
import Bean.Subject;
import Bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;

@WebServlet("/TestListSubjectExecute.action")
public class TestListSubjectExecuteController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String schoolCd = "oom"; // 仮の学校コード。ログインユーザーから取得するのが本来の形。

        String yearStr = request.getParameter("year");
        String classNum = request.getParameter("class");
        String subjectCd = request.getParameter("subject");
        String numStr = request.getParameter("num");

        School currentSchool = new School();
        currentSchool.setCd(schoolCd);


        // ★★ ここからDAOを使ったデータ取得 ★★
        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();
        List<Integer> entYears = new ArrayList<>();
        List<String> classNumsList = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        List<Integer> testNums = new ArrayList<>(); // 回数用のリスト

        try {
            // 入学年度のリストをDBから取得 (StudentDaoを使用)
            entYears = studentDao.getEntYears(currentSchool);
            // クラス番号のリストをDBから取得 (StudentDaoを使用)
            classNumsList = studentDao.getClassNums(currentSchool);
            // 科目のリストをDBから取得 (SubjectDaoを使用)
            subjects = subjectDao.filter(currentSchool);

            // 回数（テスト回数）のリストは、現状データベースに依存しないと仮定して、固定で設定
            testNums.add(1);
            testNums.add(2);

            // ★★ 追加したデバッグログ ★★
            System.out.println("DEBUG (Controller): entYears list size: " + entYears.size());
            System.out.println("DEBUG (Controller): classNums list size: " + classNumsList.size());
            System.out.println("DEBUG (Controller): subjects list size: " + subjects.size());
            // ★★ ここまで追加 ★★

            request.setAttribute("entYears", entYears);
            request.setAttribute("classNums", classNumsList);
            request.setAttribute("subjects", subjects);
            request.setAttribute("testNums", testNums);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "初期データ取得中にエラーが発生しました。");
            // エラーが発生した場合もJSPにフォワードして、エラーメッセージを表示できるようにする
            request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
            return;
        }


        // 初期表示時（パラメータが全くない場合）の処理は、上記のデータ取得後に行う
        // この if ブロックは、最初のデータ読み込みが完了した後にJSPにフォワードする役割
        // 検索パラメータがない初回アクセス時にここを通る
        if (yearStr == null && classNum == null && subjectCd == null && numStr == null) {
            request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
            return; // 初期表示処理が完了したらここで終了
        }

        // --- 検索実行時の処理 ---
        List<Test> tests = new ArrayList<>(); // 検索結果を格納するリスト
        String message = ""; // ユーザーへのメッセージ

        // 入力値の検証と変換
        Integer entYear = null;
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                message = "入学年度は不正な値です。";
            }
        }
        Integer num = null;
        if (numStr != null && !numStr.isEmpty()) {
            try {
                num = Integer.parseInt(numStr);
            } catch (NumberFormatException e) {
                message = "回数は不正な値です。";
            }
        }


        // 必須項目のチェック
        if (message.isEmpty()) {
            if (entYear == null) {
                message = "入学年度が選択されていません。";
            } else if (classNum == null || classNum.isEmpty()) {
                message = "クラスが選択されていません。";
            } else if (subjectCd == null || subjectCd.isEmpty()) {
                message = "科目が選択されていません。";
            } else if (num == null) { // 回数も必須チェックに追加
                message = "回数が選択されていません。";
            }
        }


        if (message.isEmpty()) { // すべての入力値が有効であれば検索を実行
            TestListSubjectDao testListSubjectDao = new TestListSubjectDao();

            try {
                // Subjectオブジェクトの生成 (SubjectDao#get に Schoolオブジェクトを渡す)
                Subject subject = subjectDao.get(subjectCd, currentSchool);

                if (subject == null) {
                    message = "指定された科目が見つかりませんでした。";
                } else {
                    // TestListSubjectDaoのfilterメソッドを呼び出してテスト結果を取得
                    tests = testListSubjectDao.filter(entYear, classNum, subject, num, currentSchool); // num を追加

                    if (tests.isEmpty()) {
                        message = "該当する成績情報はありませんでした。";
                    } else {
                        message = "検索結果があります。";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                message = "データベース処理中にエラーが発生しました。エラーの詳細: " + e.getMessage();
            }
        }

        // 検索結果とメッセージをリクエスト属性に設定
        request.setAttribute("tests", tests);
        request.setAttribute("message", message);

        // 検索後に選択されていた値を再度セットし、JSPで選択状態を保持できるようにする
        request.setAttribute("selectedEntYear", yearStr);
        request.setAttribute("selectedClassNum", classNum);
        request.setAttribute("selectedSubjectCd", subjectCd);
        request.setAttribute("selectedNum", numStr);

        // 結果表示JSPにフォワード
        request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
    }
}