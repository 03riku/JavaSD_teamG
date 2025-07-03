package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Subject;
import Bean.Teacher;
import dao.SubjectDao;

@WebServlet("/subject_update")
public class Subject_updateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Teacher teacher = (session != null) ? (Teacher) session.getAttribute("teacher") : null;
        if (teacher == null || teacher.getSchool() == null) {
            request.setAttribute("errorMessage", "ログインが必要です。");
            request.getRequestDispatcher("LOGO001.jsp").forward(request, response);
            return;
        }

        String cd = request.getParameter("no"); // フォームの name="no" に合わせる
        if (cd == null || cd.trim().isEmpty()) {
            request.setAttribute("errorMessage", "科目コードが指定されていません。");
            request.getRequestDispatcher("/log/error.jsp").forward(request, response);
            return;
        }

        try {
            SubjectDao dao = new SubjectDao();
            Subject subject = dao.get(cd, teacher.getSchool());
            if (subject == null) {
                request.setAttribute("errorMessage", "該当する科目が見つかりませんでした。");
                request.getRequestDispatcher("/log/error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("subject", subject);
            request.setAttribute("no", subject.getCd());
            request.getRequestDispatcher("/log/SBJM004.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
