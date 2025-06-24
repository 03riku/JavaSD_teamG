package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.School;
import Bean.Subject;
import Bean.Teacher;
import dao.SubjectDao;

@WebServlet("/SBJM006")
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

        String cd = request.getParameter("cd"); // 削除対象の科目コード

        SubjectDao subjectDao = new SubjectDao();
        School school = teacher.getSchool();
        Subject subject = null;
        String errorMessage = null;

        try {
            subject = subjectDao.get(cd, school); // 科目コードと学校で科目を取得
            if (subject.getName() == null) { // 科目が見つからない場合
                errorMessage = "指定された科目が見つかりません。";
            }
        } catch (Exception e) {
            errorMessage = "科目データの取得中にエラーが発生しました: " + e.getMessage();
            e.printStackTrace();
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("SBJM001.jsp").forward(request, response); // エラーの場合は一覧に戻る
            return;
        }

        request.setAttribute("subject", subject);
        request.getRequestDispatcher("SBJM006.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}