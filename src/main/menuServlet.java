package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/menu/MMNU001"})
public class menuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // セッションからユーザー情報を取得
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            // ログインしていない場合、ログイン画面にリダイレクト
            resp.sendRedirect(req.getContextPath() + "/log/LOGI001.jsp");
            return;
        }

        // メニュー画面を表示
        req.getRequestDispatcher("/log/MMNU001.jsp").forward(req, resp);
    }
}
