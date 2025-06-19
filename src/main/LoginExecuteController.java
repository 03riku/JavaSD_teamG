package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Student;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/log/MMNU001"})  // JSPのactionに合わせて修正
public class LoginExecuteController extends CommonServlet {
    private static final long serialVersionUID = 1L;

    protected void post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("debug-chk002");
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
            	System.out.println("debug-chk001");
                request.getRequestDispatcher("/log/LOGI001").forward(request, response);
                return;
            }

            // 2. パスワードだけ別で取得
            String dbPassword = null;
            String sql = "SELECT password FROM Teacher WHERE no = ?";

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
                request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //メニューに続く
            request.getRequestDispatcher("/log/MMNU001.jsp").forward(request, response);
        }

    }
    protected void get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
