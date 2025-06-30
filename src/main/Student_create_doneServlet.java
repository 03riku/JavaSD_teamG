package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Student;
import Bean.Teacher;
import dao.StudentDao; // パッケージ名に注意

@WebServlet("/STDM003")
public class Student_create_doneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("=====================================================================");

        request.setCharacterEncoding("UTF-8");

        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");

        int entYear = 0;
        try {
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            System.out.println("===== errorMessage:入学年度が不正です。数値を入力してください。");
            request.setAttribute("errorMessage", "入学年度が不正です。数値を入力してください。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(true);

        HttpSession session = request.getSession();
        System.out.println("session:" + session);
        Teacher Getteacher = (Teacher) session.getAttribute("teacher");
        System.out.println("===============================================");
        System.out.println("Getteacher:" + Getteacher);
        System.out.println("Getteacher.getSchool():" +  Getteacher.getSchool());
        //student.setSchool(Getteacher.getSchool());

        StudentDao studentDao = new StudentDao();
        // boolean success = false; // ★この行を削除またはコメントアウト★
        try {
            studentDao.insert(student); // ★戻り値を受け取らないように修正★

            // insertがvoidなので、成功したかどうかは例外が投げられなかったことで判断します。
            // そのため、ここでは登録成功として扱います。
            request.setAttribute("message", "学生情報が正常に登録されました。");
            request.getRequestDispatcher("/log/STDM003.jsp").forward(request, response);
            // return; // forward後は通常returnします
        } catch (Exception e) {
            e.printStackTrace(); // エラーの詳細をコンソールに出力
            request.setAttribute("errorMessage", "データベースエラーが発生しました：" + e.getMessage());
            request.getRequestDispatcher("/log/ERRO001.jsp").forward(request, response);
            return; // エラー時はここで処理を終了
        }

        // ここに到達することは、通常は正常終了（insert成功）を意味します
        // すでにtryブロック内でフォワードしているので、ここでのフォワードは不要です。
        // request.getRequestDispatcher("/log/STDM003.jsp").forward(request, response);
    }
}