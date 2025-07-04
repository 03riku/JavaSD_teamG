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
        TestDao testDao = new TestDao();

        // ドロップダウンリスト用のデータを格納するリスト
        List<Integer> entYears = new ArrayList<>();
        List<String> classNums = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        List<Integer> numList = new ArrayList<>(); // テスト回数用 (JSPで使用しない場合は削除可能)

        // 検索結果を格納するリストとメッセージ
        List<Test> testResults = new ArrayList<>();
        String message = "";
        String selectedSubjectName = ""; // 科目名表示用 (TestListSubjectExecute.action にも必要かも)

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

            // テスト回数リスト (JSPで使用しない場合は削除可)
            numList.add(1);
            numList.add(2);
            request.setAttribute("numSet", numList);


            // --- リクエストパラメータの取得 ---
            String paramYearStr = request.getParameter("year");
            String paramClassNum = request.getParameter("class_num");
            String paramSubjectCd = request.getParameter("subject");
            String paramStudentId = request.getParameter("studentId"); // 学生ID


            // 検索パラメータをJSPに送り返し、選択状態を維持する
            request.setAttribute("paramYear", paramYearStr);
            request.setAttribute("paramClassNum", paramClassNum);
            request.setAttribute("paramSubjectCd", paramSubjectCd);
            request.setAttribute("paramStudentId", paramStudentId);


            // デバッグ出力 - 取得したパラメータを確認
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Retrieved Parameters:");
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):    year: " + paramYearStr);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):    class_num: " + paramClassNum);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):    subject: " + paramSubjectCd);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):    studentId: " + paramStudentId);


            // ★ここから検索ロジックの修正★
            if (paramStudentId != null && !paramStudentId.isEmpty()) {
                // 学生番号が入力されている場合（学生IDによる検索を優先）
                System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Searching by student ID: " + paramStudentId);
                testResults = testDao.filterByStudentId(paramStudentId, schoolCd); // schoolCdも渡すように変更

                if (testResults.isEmpty()) {
                    message = "該当する学生の成績データが見つかりませんでした。";
                } else {
                    message = "学生番号: " + testResults.get(0).getStudent().getNo() + " の成績を表示しています。";
                }
            } else if ((paramYearStr != null && !paramYearStr.isEmpty()) &&
                       (paramClassNum != null && !paramClassNum.isEmpty()) &&
                       (paramSubjectCd != null && !paramSubjectCd.isEmpty())) {
                // 入学年度、クラス、科目が全て選択されている場合
                System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Searching by year, class, subject.");
                Integer year = Integer.parseInt(paramYearStr);
                testResults = testDao.filter(year, paramClassNum, paramSubjectCd, schoolCd);

                if (testResults.isEmpty()) {
                    message = "指定された科目条件の成績は見つかりませんでした。";
                } else {
                    // 選択された科目名を取得してJSPに渡す（もしあれば）
                    Subject selectedSubject = subjectDao.get(paramSubjectCd, currentSchool); // getメソッドにSchoolを渡す想定
                    if (selectedSubject != null) {
                        selectedSubjectName = selectedSubject.getName();
                    }
                    request.setAttribute("selectedSubjectName", selectedSubjectName); // GRML001.jsp向けに必要？
                    message = "検索結果が見つかりました。";
                }
            } else {
                // どの検索条件も指定されていない、または条件が不完全な場合
                message = "科目情報を選択または学生情報を入力して検索ボタンをクリックしてください";
                System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): No complete search parameters provided.");
            }
            // ★ここまで検索ロジックの修正★

            request.setAttribute("testResults", testResults);
            request.setAttribute("message", message);

            request.getRequestDispatcher("/log/GRMR001.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "成績参照中にエラーが発生しました。詳細はシステム管理者にお問い合わせください。");
            System.err.println("ERROR (" + this.getClass().getSimpleName() + "): " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}