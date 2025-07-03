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

@WebServlet("/subject_update_done")
public class Subject_update_doneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Teacher teacher = (session != null) ? (Teacher) session.getAttribute("teacher") : null;
        if (teacher == null || teacher.getSchool() == null) {
            request.setAttribute("errorMessage", "ログインが必要です。");
            request.getRequestDispatcher("LOGO001.jsp").forward(request, response);
            return;
        }

        String cd = request.getParameter("no");
        String name = request.getParameter("name");

        // バリデーション例
        boolean hasError = false;
        if (cd == null || cd.trim().isEmpty()) {
            request.setAttribute("errorMessage", "科目コードが空です。");
            hasError = true;
        }
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorSubjectNameEmpty", "科目名を入力してください。");
            hasError = true;
        }
        if (hasError) {
            request.setAttribute("no", cd);
            Subject s = new Subject();
            s.setCd(cd);
            s.setName(name);
            request.setAttribute("subject", s);
            request.getRequestDispatcher("/log/SBJM004.jsp").forward(request, response);
            return;
        }

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        try {
            SubjectDao dao = new SubjectDao();
            boolean ok = dao.save(subject);
            if (!ok) {
                request.setAttribute("errorMessage", "科目の更新に失敗しました。");
                request.getRequestDispatcher("/log/error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("successMessage", "科目が正常に更新されました。");
            // 完了表示用に再度 no/name をセット
            request.setAttribute("no", cd);
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("/log/SBJM005.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
