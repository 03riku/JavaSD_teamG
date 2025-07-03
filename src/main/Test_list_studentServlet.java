package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.School;
import Bean.Subject;
import Bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;

@WebServlet("/TestListStudentExecute.action")
public class Test_list_studentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String schoolCd = "oom"; // 仮で固定。本番ではセッションから取得するべきです。
        School currentSchool = new School();
        currentSchool.setCd(schoolCd);

        // DAOのインスタンス化
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao(); // TestDaoをインスタンス化

        // ドロップダウンリスト用のデータを格納するリスト
        List<Integer> entYears = new ArrayList<>();
        List<String> classNums = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        List<Integer> numList = new ArrayList<>(); // テスト回数用

        // 検索結果を格納するリストとメッセージ
        List<Test> testResults = new ArrayList<>();
        String message = "";

        try {
            // --- ドロップダウンリストデータの取得 (常に必要) ---
            entYears = studentDao.getEntYears(currentSchool);
            request.setAttribute("entYears", entYears);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): entYears list size: " + entYears.size());

            classNums = classNumDao.filter(schoolCd);
            request.setAttribute("classNums", classNums);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): classNums list size: " + classNums.size());

            subjects = subjectDao.filter(currentSchool);
            request.setAttribute("subjects", subjects);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): subjects list size: " + subjects.size());

            // テスト回数リスト (仮に1回, 2回を設定)
            numList.add(1);
            numList.add(2);
            request.setAttribute("numSet", numList);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): numList size: " + numList.size());


            // --- リクエストパラメータの取得 (検索ボタンが押された場合) ---
            String paramYearStr = request.getParameter("year");
            String paramClassNum = request.getParameter("class_num");
            String paramSubjectCd = request.getParameter("subject");
            String paramStudentId = request.getParameter("studentId"); // JSPからの学生IDは取得するが、TestDaoには渡さない


            // 検索パラメータをJSPに送り返し、選択状態を維持する
            request.setAttribute("paramYear", paramYearStr);
            request.setAttribute("paramClassNum", paramClassNum);
            request.setAttribute("paramSubjectCd", paramSubjectCd);
            request.setAttribute("paramStudentId", paramStudentId);
            // request.setAttribute("paramNum", paramNumStr);


            // デバッグ出力 - 取得したパラメータを確認
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Retrieved Parameters:");
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):   year: " + paramYearStr);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):   class_num: " + paramClassNum);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):   subject: " + paramSubjectCd);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):   studentId: " + paramStudentId);


            // 検索条件が指定されているかチェック
            // TestDao.filterに渡せる引数のみを条件に含める
            if ((paramYearStr != null && !paramYearStr.isEmpty()) ||
                (paramClassNum != null && !paramClassNum.isEmpty()) ||
                (paramSubjectCd != null && !paramSubjectCd.isEmpty())) { // ★学生IDはここでは条件に含めない

                System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Entering search logic (excluding studentId if present)...");

                Integer year = null;
                if (paramYearStr != null && !paramYearStr.isEmpty()) {
                    year = Integer.parseInt(paramYearStr);
                }

                // ★ここを修正！ TestDao.filterメソッドを、現在のTestDaoに存在する4つの引数で呼び出す
                // paramStudentId はこの呼び出しでは使用しない
                testResults = testDao.filter(year, paramClassNum, paramSubjectCd, schoolCd);


                if (testResults.isEmpty()) {
                    message = "指定された条件の成績は見つかりませんでした。";
                } else {
                    message = "検索結果が見つかりました。";
                }
                System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Search results size: " + testResults.size());

            } else {
                // 検索条件が何も指定されていない場合の初期表示メッセージ
                // 学生IDが入力されているが、他の条件がない場合もここに含まれる
                message = "科目情報を選択または学生情報を入力して検索ボタンをクリックしてください";
                System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): No search parameters or studentId only. Displaying initial message.");
            }

            // 検索結果とメッセージをJSPに設定
            request.setAttribute("testResults", testResults);
            request.setAttribute("message", message);

            // GRMR001.jsp にフォワード
            request.getRequestDispatcher("/log/GRMR001.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "成績参照中にエラーが発生しました。詳細はシステム管理者にお問い合わせください。");
            System.err.println("ERROR (" + this.getClass().getSimpleName() + "): " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}