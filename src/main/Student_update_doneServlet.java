package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.Student;
import dao.StudentDao;

@WebServlet("/student_update_done")
public class Student_update_doneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 文字エンコーディング（POST対策）
        request.setCharacterEncoding("UTF-8");

        // フォームからの値を取得
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String entYear = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");

        try {
            // Student オブジェクトにセット
            Student student = new Student();
            student.setNo(no);
            student.setName(name);
            student.setEntYear(Integer.parseInt(entYear));
            student.setClassNum(classNum);
            // attend や school_cd の更新が必要ならここで追加

            // DAO を使って更新処理
            StudentDao dao = new StudentDao();
            dao.update(student); // ★DAOにupdateメソッドが必要です

            // 更新成功後に完了画面へ遷移
            request.getRequestDispatcher("/log/STDM005.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "更新中にエラーが発生しました");
            request.getRequestDispatcher("/log/ERRO001.jsp").forward(request, response);
        }
    }
}
