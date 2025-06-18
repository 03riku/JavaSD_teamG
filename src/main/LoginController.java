package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/log/LOGI001"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GETリクエスト時にログイン画面を表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("debug-chk003");
        // login.jspの場所に合わせてパスを変更
        request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
    }

    // POSTリクエストが来た場合はGETへ委譲
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("debug-chk004");
        doGet(request, response);
    }
}