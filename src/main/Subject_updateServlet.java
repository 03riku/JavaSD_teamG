package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Subject;
import dao.SubjectDao;

@WebServlet("/subject_update")
public class Subject_updateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータ（科目コード）取得
        String cd = request.getParameter("cd");

        try {
            // 科目情報取得
            SubjectDao dao = new SubjectDao();
            // School 情報は別途取得が必要なので仮に null を渡す前提ではなく
            // Teacher セッションなどで渡してください
            HttpSession session = request.getSession(false);
            Bean.Teacher teacher = (session != null) ? (Bean.Teacher) session.getAttribute("teacher") : null;
            Bean.School school = (teacher != null) ? teacher.getSchool() : null;

            Subject subject = dao.get(cd, school);

            if (subject == null) {
                request.setAttribute("error", "該当する科目が見つかりませんでした。");
                request.getRequestDispatcher("/log/error.jsp").forward(request, response);
                return;
            }

            // 取得した科目情報をリクエストスコープに保存
            request.setAttribute("subject", subject);

            // フォーム表示JSPへフォワード
            request.getRequestDispatcher("/log/SBJM004.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
