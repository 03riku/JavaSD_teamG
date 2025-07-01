package main;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Subject;
import Bean.Teacher;

@WebServlet(urlPatterns = "/log/SBJM002")
public class SubjectCreateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // 既存のセッションを取得（新規作成しない）

        // ログインチェック
        Teacher teacher = (session != null) ? (Teacher) session.getAttribute("teacher") : null;
        if (teacher == null || teacher.getSchool() == null) {
            response.sendRedirect("LOGO001.jsp");
            return;
        }

        // 新規入力用のSubjectオブジェクトをセット
        request.setAttribute("subject", new Subject());

        // エラーメッセージなどの属性をクリア
        String[] attributesToClear = {
            "errorSubjectCdEmpty",
            "errorSubjectCdLength",
            "errorSubjectCdExists",
            "errorSubjectNameEmpty",
            "successMessage",
            "errorMessage"
        };
        for (String attr : attributesToClear) {
            request.removeAttribute(attr);
        }

        // 入力画面へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/SBJM002.jsp");
        dispatcher.forward(request, response);
    }
}
