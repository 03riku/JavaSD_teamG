package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Teacher;
import dao.TeacherDao;

@WebServlet("/log/LoginServlet")  // JSPの<form action>に合わせる
public class LoginExecuteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = null;

        try {
            // Teacher情報を取得
            teacher = teacherDao.get(id);

            if (teacher == null) {
                // IDが存在しない場合、ログイン画面（LOGI001.jsp）へ戻す
                request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
                return;
            }

            // パスワードチェック
            if (teacher.getPassword() != null && teacher.getPassword().equals(password)) {
                // ログイン成功：セッションにteacherを保存
                HttpSession session = request.getSession();
                session.setAttribute("teacher", teacher);

                // メインメニュー画面へリダイレクト
                response.sendRedirect(request.getContextPath() + "/log/MMNU001.jsp");
            } else {
                // パスワード不一致：ログイン画面へ戻す
                request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // エラー時もログイン画面へ戻す
            request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
        }
    }
}
