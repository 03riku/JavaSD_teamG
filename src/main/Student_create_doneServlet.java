package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/STDM003")
public class Student_create_doneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // POSTリクエストで登録処理
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // フォームのパラメータ取得例（名前・年・クラスなど）
        String name = request.getParameter("name");
        String entYear = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");

        // TODO: ここでDAOを使ってDB登録処理を書く

        // 登録成功後は完了画面にフォワード
        request.getRequestDispatcher("/log/STDM003.jsp").forward(request, response);
    }
}
