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

@WebServlet("/SBJM003")
public class SubjectCreateExecuteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);

        HttpSession session = request.getSession(false);
        if (session == null) {
            showError(request, response, "セッションが存在しません。再ログインしてください。");
            return;
        }

        Subject teacher = (Subject) session.getAttribute("subject");
        if (teacher == null || teacher.getSchool() == null) {
            showError(request, response, "セッションに教師情報または学校情報がありません。");
            return;
        }

        subject.setSchool(teacher.getSchool());

        SubjectDao dao = new SubjectDao();
        try {
            boolean ok = dao.save(subject);
            if (!ok) {
                showError(request, response, "科目の登録に失敗しました。");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError(request, response, "データベースエラーが発生しました：" + e.getMessage());
            return;
        }

        // PRG パターン: 登録後に GET 先にリダイレクト
        HttpSession s2 = request.getSession();
        s2.setAttribute("message", "科目情報が正常に登録されました。");
        response.sendRedirect(request.getContextPath() + "/SBJM003OK"); // 完了ページへリダイレクト
    }

    /** エラーメッセージを ERRO001.jsp に転送 */
    private void showError(HttpServletRequest req, HttpServletResponse res, String msg)
            throws ServletException, IOException {
        req.setAttribute("errorMessage", msg);
        req.getRequestDispatcher("/log/ERRO001.jsp").forward(req, res);
    }
}
