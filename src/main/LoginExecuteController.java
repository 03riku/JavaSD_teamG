package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

<<<<<<< HEAD
import Bean.Teacher;
import dao.TeacherDao;
=======
import Bean.Student;
import dao.StudentDao;
import tool.CommonServlet;
>>>>>>> branch 'master' of https://github.com/03riku/JavaSD_teamG.git

<<<<<<< HEAD
@WebServlet("/LoginServlet")  // JSPの<form action>に合わせて修正
public class LoginExecuteController extends HttpServlet {
=======
@WebServlet(urlPatterns={"/log/MMNU001"})  // JSPのactionに合わせて修正
public class LoginExecuteController extends CommonServlet {
>>>>>>> branch 'master' of https://github.com/03riku/JavaSD_teamG.git
    private static final long serialVersionUID = 1L;

    protected void post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("debug-chk002");
    	request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = null;

        try {
            teacher = teacherDao.get(id);

<<<<<<< HEAD
            if (teacher == null) {
                // IDが存在しない場合はログイン画面に戻す
                request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
=======
            if (student == null) {
                // IDがない → ログイン画面に戻す（エラーメッセージ無し）
            	System.out.println("debug-chk001");
                request.getRequestDispatcher("/log/LOGI001").forward(request, response);
>>>>>>> branch 'master' of https://github.com/03riku/JavaSD_teamG.git
                return;
            }

            if (teacher.getPassword() != null && teacher.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("teacher", teacher);
                response.sendRedirect(request.getContextPath() + "/log/MMNU001.jsp");
            } else {
<<<<<<< HEAD
=======
                // パスワード不一致 → ログイン画面に戻す（エラーメッセージ無し）
>>>>>>> branch 'master' of https://github.com/03riku/JavaSD_teamG.git
                request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
<<<<<<< HEAD
            request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
=======
            //メニューに続く
            request.getRequestDispatcher("/log/MMNU001.jsp").forward(request, response);
>>>>>>> branch 'master' of https://github.com/03riku/JavaSD_teamG.git
        }

    }
    protected void get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
