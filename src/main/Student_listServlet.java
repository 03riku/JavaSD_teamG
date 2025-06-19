package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.Student;
import dao.StudentDao;

@WebServlet("/STDM001")
public class Student_listServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            StudentDao dao = new StudentDao();
            List<Student> students = dao.findAll();
            request.setAttribute("students", students);
            RequestDispatcher rd = request.getRequestDispatcher("/STDM001.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
