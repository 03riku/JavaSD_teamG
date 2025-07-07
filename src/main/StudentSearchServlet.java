package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.School;
import Bean.Student;
import dao.StudentDao;

@WebServlet("/StudentSearch")
public class StudentSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String isAttendStr = request.getParameter("isAttend");

        // ログイン中の学校情報をセッションから取得（例）
        School school = (School) request.getSession().getAttribute("school");

        // エラーメッセージ用
        String errorMessage = null;
        List<Student> students = null;

        try {
            boolean isAttend = "true".equals(isAttendStr);

            StudentDao dao = new StudentDao();

            // フィルタ条件に応じてDaoのメソッドを呼び分け
            if (entYearStr != null && !entYearStr.isEmpty()) {
                int entYear = Integer.parseInt(entYearStr);

                if (classNum != null && !classNum.isEmpty()) {
                    // 学校・入学年・クラス・在学中
                    students = dao.filter(school, entYear, classNum, isAttend);
                } else {
                    // 学校・入学年・在学中
                    students = dao.filter(school, entYear, isAttend);
                }
            } else {
                if (classNum != null && !classNum.isEmpty()) {
                    // 入学年度が未指定でクラスのみ指定の場合は要件に合わせて処理を決める（ここでは全件取得）
                    students = dao.findAll(); // 例：全部
                } else {
                    // 学校・在学中
                    students = dao.filter(school, isAttend);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "検索中にエラーが発生しました: " + e.getMessage();
        }

        // JSPに渡す
        request.setAttribute("students", students);
        request.setAttribute("studentCount", students != null ? students.size() : 0);
        request.setAttribute("errorMessage", errorMessage);

        // 検索フォームの入力値を戻す用
        request.setAttribute("entYear", entYearStr);
        request.setAttribute("classNum", classNum);
        request.setAttribute("isAttend", isAttendStr);

        // JSPへフォワード
        RequestDispatcher rd = request.getRequestDispatcher("/log/STDM001.jsp");
        rd.forward(request, response);
    }
}
