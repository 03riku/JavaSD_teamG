package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Student;
import dao.StudentDao;

@WebServlet("/log/LoginServlet")  // JSPのactionに合わせて修正
public class LoginExecuteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        StudentDao studentDao = new StudentDao();
        Student student = null;

        try {
            // 1. IDでStudentを取得（パスワードなし）
            student = studentDao.get(id);

            if (student == null) {
                // IDがない → ログイン画面に戻す（エラーメッセージ無し）
                request.getRequestDispatcher("/log/LOGI.jsp").forward(request, response);
                return;
            }

            // 2. パスワードだけ別で取得
            String dbPassword = null;
            String sql = "SELECT password FROM Student WHERE no = ?";

            try (Connection con = studentDao.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dbPassword = rs.getString("password");
                    }
                }
            }

            // 3. パスワードチェック
            if (dbPassword != null && dbPassword.equals(password)) {
                // ログイン成功 → セッションにStudentをセット
                HttpSession session = request.getSession();
                session.setAttribute("student", student);

                // メインメニュー画面へリダイレクト
                response.sendRedirect(request.getContextPath() + "/log/MMNU001.jsp");
            } else {
                // パスワード不一致 → ログイン画面に戻す（エラーメッセージ無し）
                request.getRequestDispatcher("/log/LOGI.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // システムエラーでもログイン画面に戻す（エラーメッセージ無し）
            request.getRequestDispatcher("/log/LOGI.jsp").forward(request, response);
        }
    }
}
