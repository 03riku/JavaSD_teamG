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

@WebServlet("/LoginServlet")
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
            student = studentDao.get(id);

            if (student == null) {
                // IDがない場合はログイン画面へ戻す
                response.sendRedirect("log/LOGI.jsp");
                return;
            }

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

            if (dbPassword != null && dbPassword.equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("student", student);
                response.sendRedirect("log/MMNU001.jsp");
            } else {
                // パスワード不一致はログイン画面にリダイレクト
                response.sendRedirect("log/LOGI.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            // システムエラー時もログイン画面へリダイレクト
            response.sendRedirect("log/LOGI.jsp");
        }
    }
}