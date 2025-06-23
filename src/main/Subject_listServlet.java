package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.School; // School Beanをインポート
import Bean.Subject; // Subject Beanをインポート
import Bean.Teacher; // Teacher Beanをインポート (ユーザー情報をセッションから取得するため)
import dao.SubjectDao;

@WebServlet("/SBJM001")
public class Subject_listServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher"); // ログイン中の教員情報を取得

        if (teacher == null) {
            // ユーザーがログインしていない場合はログインページにリダイレクト
            request.getRequestDispatcher("login.jsp").forward(request, response); // 例: ログインページへのパス
            return;
        }

        SubjectDao subjectDao = new SubjectDao();
        School school = teacher.getSchool(); // ログインしている教員の学校情報を取得

        List<Subject> subjects = null;
        String errorMessage = null;

        try {
            // SubjectDaoのfilterメソッドを呼び出して科目のリストを取得
            subjects = subjectDao.filter(school);
        } catch (Exception e) {
            errorMessage = "科目データの取得中にエラーが発生しました: " + e.getMessage();
            e.printStackTrace();
        }

        // 取得した科目リストとエラーメッセージをリクエスト属性に設定
        request.setAttribute("subjects", subjects);
        request.setAttribute("errorMessage", errorMessage);

        // SBJM001.jspにフォワード
        request.getRequestDispatcher("SBJM001.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}