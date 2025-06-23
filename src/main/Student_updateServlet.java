package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.Student;
import dao.StudentDao;

@WebServlet("/student_update")
public class Student_updateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータ（学生番号）取得
        String no = request.getParameter("no");

        try {
            // 学生情報取得
            StudentDao dao = new StudentDao();
            Student student = dao.get(no);

            if (student == null) {
                request.setAttribute("error", "該当する学生が見つかりませんでした。");
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                return;
            }

            // 取得した学生情報をリクエストスコープに保存
            request.setAttribute("student", student);

            // フォーム表示JSPへフォワード
            request.getRequestDispatcher("/log/STDM005.jsp").forward(request, response);


        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
