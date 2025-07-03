package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Teacher;

@WebServlet(urlPatterns={"/main/subject_delete"})
public class Subject_deleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        String cd = request.getParameter("cd");// 削除対象の科目コード
        String name = request.getParameter("name");



        request.setAttribute("cd", cd);
        request.setAttribute("name", name);
        request.getRequestDispatcher("/log/SBJM006.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}