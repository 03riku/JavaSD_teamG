package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/log/STDM002")
public class Student_createServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GETリクエストで学生登録フォームを表示
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // JSPにフォワード（画面表示）
    	request.getRequestDispatcher("/STDM002.jsp").forward(request, response);
    }
}
