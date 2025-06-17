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

@WebServlet("/LoginServlet")  // JSPの<form action>に合わせて修正
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
            teacher = teacherDao.get(id);

            if (teacher == null) {
                // IDが存在しない場合はログイン画面に戻す
                request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
                return;
            }

            if (teacher.getPassword() != null && teacher.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("teacher", teacher);
                response.sendRedirect(request.getContextPath() + "/log/MMNU001.jsp");
            } else {
                request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
        }
    }
}
