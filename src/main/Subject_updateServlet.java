package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Teacher;

@WebServlet("/main/subject_update")
public class Subject_updateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Teacher teacher = (session != null) ? (Teacher) session.getAttribute("teacher") : null;
        try{
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

            request.setAttribute("cd", cd);
            request.setAttribute("name", name);
            System.out.println("##############################");
            System.out.println(cd);
            System.out.println(name);
            request.getRequestDispatcher("/log/SBJM004.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
