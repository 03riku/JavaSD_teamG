// src/main/java/servlet/SubjectDeleteDoneServlet.java
package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Teacher; // Teacher Beanもセッションから取得すると仮定

@WebServlet("/SBJM007") // このURLにアクセスするとこのサーブレットが実行されます
public class Subject_update_doneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // リクエストのエンコーディングを設定

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher"); // セッションから先生情報を取得

        // ログインしていない場合はログインページへリダイレクト
        // 削除完了画面では特に学校情報が必要ないかもしれないが、システムの整合性のためチェック
        if (teacher == null || teacher.getSchool() == null) {
            request.setAttribute("errorMessage", "ログインが必要です。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("LOGO001.jsp"); // ログインページへリダイレクト
            dispatcher.forward(request, response);
            return;
        }

        // 特に処理は不要。単に削除完了JSPにフォワードするだけ。
        // もし、削除された科目の情報などをJSPに表示したい場合は、
        // ここでリクエストスコープにsetAttributeする。
        // 例: request.setAttribute("deletedSubjectName", "削除された科目名");

        RequestDispatcher dispatcher = request.getRequestDispatcher("SBJM007.jsp");
        dispatcher.forward(request, response);
    }

    // もしPOSTでこのサーブレットが呼ばれる可能性があるなら、doPostも実装する
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // POSTでもGETと同じ処理を行う
    }
}