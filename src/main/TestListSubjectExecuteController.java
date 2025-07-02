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
import dao.SubjectDao;
import dao.TestListSubjectDao;
// import dao.SchoolDao; // もしSchoolDaoがあるなら、ここでインポートしてください

@WebServlet("/TestListSubjectExecute.action")
public class TestListSubjectExecuteController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 仮の学校コード。ログインユーザーから取得するのが本来の形。
        // ※このschoolCdは、現在のシステムが単一の学校で動作していることを前提としています。
        String schoolCd = "oom";

        String yearStr = request.getParameter("year");
        String classNum = request.getParameter("class");
        String subjectCd = request.getParameter("subject");

        // Schoolオブジェクトをここで作成し、全てのDAO呼び出しで共通して利用
        // これにより、SubjectDao#filter(School) や SubjectDao#get(String, School) の引数エラーを解消
        School currentSchool = new School();
        currentSchool.setCd(schoolCd);
        // もしSchoolDaoがあり、学校名なども含めた完全なSchoolオブジェクトを取得したい場合は、以下のようにします。
        // try {
        //     SchoolDao schoolDao = new SchoolDao();
        //     currentSchool = schoolDao.get(schoolCd); // SchoolDaoから完全なSchoolオブジェクトを取得
        //     if (currentSchool == null) {
        //         // 学校が見つからない場合のエラーハンドリング
        //         request.setAttribute("message", "指定された学校コードが見つかりません。");
        //         request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
        //         return;
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     request.setAttribute("message", "学校情報の取得中にエラーが発生しました。");
        //     request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
        //     return;
        // }


        // 初期表示時（パラメータが全くない場合）の処理
        if (yearStr == null && classNum == null && subjectCd == null) {
            SubjectDao subjectDao = new SubjectDao();
            try {
                // 入学年度のリスト（仮データ、実際にはDBから取得するのが望ましい）
                List<Integer> entYears = new ArrayList<>();
                entYears.add(2023);
                entYears.add(2024);
                entYears.add(2025);
                request.setAttribute("entYears", entYears);

                // クラス番号のリスト（仮データ、実際にはDBから取得するのが望ましい）
                List<String> classNumsList = new ArrayList<>();
                classNumsList.add("101");
                classNumsList.add("102");
                classNumsList.add("131");
                classNumsList.add("132"); // 例として追加
                request.setAttribute("classNums", classNumsList);

                // 科目のリストを取得 (SubjectDao#filter に Schoolオブジェクトを渡す)
                List<Subject> subjects = subjectDao.filter(currentSchool);
                request.setAttribute("subjects", subjects);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "初期データ取得中にエラーが発生しました。");
            }
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

        // 必須項目のチェック
        // messageが既に設定されている場合はスキップ
        if (message.isEmpty()) {
            if (entYear == null) {
                message = "入学年度が選択されていません。";
            } else if (classNum == null || classNum.isEmpty()) {
                message = "クラスが選択されていません。";
            } else if (subjectCd == null || subjectCd.isEmpty()) {
                message = "科目が選択されていません。";
            }
        }


        if (message.isEmpty()) { // すべての入力値が有効であれば検索を実行
            TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
            SubjectDao subjectDao = new SubjectDao();

            try {
                // Subjectオブジェクトの生成 (SubjectDao#get に Schoolオブジェクトを渡す)
                Subject subject = subjectDao.get(subjectCd, currentSchool);

                if (subject == null) {
                    message = "指定された科目が見つかりませんでした。";
                } else {
                    // TestListSubjectDaoのfilterメソッドを呼び出してテスト結果を取得
                    // currentSchool オブジェクトを TestListSubjectDao#filter の school 引数として渡す
                    tests = testListSubjectDao.filter(entYear, classNum, subject, currentSchool);

                    if (tests.isEmpty()) {
                        message = "該当する成績情報はありませんでした。";
                    } else {
                        message = "検索結果があります。";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                message = "データベース処理中にエラーが発生しました。エラーの詳細: " + e.getMessage(); // エラーメッセージを追加
            }
        }

        // 検索結果とメッセージをリクエスト属性に設定
        request.setAttribute("tests", tests);
        request.setAttribute("message", message);

        // プルダウンリストの再表示のために必要なデータを再設定
        // (検索条件を保持し、再度表示するため)
        SubjectDao subjectDaoForPulldown = new SubjectDao(); // 新しいインスタンス（または既存のsubjectDaoを再利用しても良い）
        try {
            List<Integer> entYears = new ArrayList<>();
            entYears.add(2023);
            entYears.add(2024);
            entYears.add(2025);
            request.setAttribute("entYears", entYears);

            List<String> classNumsList = new ArrayList<>();
            classNumsList.add("101");
            classNumsList.add("102");
            classNumsList.add("131");
            classNumsList.add("132"); // 例として追加
            request.setAttribute("classNums", classNumsList);

            // 科目のリストを再取得 (SubjectDao#filter に Schoolオブジェクトを渡す)
            List<Subject> subjects = subjectDaoForPulldown.filter(currentSchool);
            request.setAttribute("subjects", subjects);

            // 検索後に選択されていた値を再度セットし、JSPで選択状態を保持できるようにする
            request.setAttribute("selectedEntYear", yearStr);
            request.setAttribute("selectedClassNum", classNum);
            request.setAttribute("selectedSubjectCd", subjectCd);

        } catch (Exception e) {
            e.printStackTrace();
            // ここでエラーが発生しても、既にテスト結果とメッセージは設定されているので、
            // 処理を中断せずにフォワードを続行します。
        }

        // 結果表示JSPにフォワード
        request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
    }
}