package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Subject;
import Bean.Teacher;

// Teacher BeanとSchool Beanはご自身のプロジェクトの実際のクラスを使用してください

@WebServlet("/SubjectCreateAction") // 科目登録リンクの遷移先
public class SubjectCreateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        // ログインチェック
        if (teacher == null || teacher.getSchool() == null) {
            response.sendRedirect("LOGO001.jsp");
            return;
        }

        // ここでは、空のSubjectオブジェクトを渡すか、何も渡さずにJSPで新規入力モードと判断させる
        // シーケンス図の「入力された値をクリア」に対応するなら、新しいSubjectオブジェクトをセット
        request.setAttribute("subject", new Subject()); // 新規入力のため空のSubjectをセット

        // エラーメッセージなどはこの時点ではクリアされている想定
        request.removeAttribute("errorSubjectCdEmpty");
        request.removeAttribute("errorSubjectCdLength");
        request.removeAttribute("errorSubjectCdExists");
        request.removeAttribute("errorSubjectNameEmpty");
        request.removeAttribute("successMessage");
        request.removeAttribute("errorMessage");

        // シーケンス図の「subject_create.jspへ」に対応
        request.getRequestDispatcher("subject_create.jsp").forward(request, response);
    }
}