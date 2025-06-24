// src/main/java/main/Subject_updateServlet.java
package main; // パッケージ名は 'main'

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/SubjectUpdateDisplay") // 科目変更画面を表示するためのURL
public class Subject_updateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // リクエストのエンコーディングを設定

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher"); // セッションから先生情報を取得

        // 未ログインまたは学校情報がない場合はログイン画面へリダイレクト
        if (teacher == null || teacher.getSchool() == null) {
            request.setAttribute("errorMessage", "ログインが必要です。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("LOGO001.jsp");
            dispatcher.forward(request, response);
            return;
        }

        School school = teacher.getSchool(); // 先生が所属する学校を取得

        // パラメータから科目コードを取得 (例: SRJM002.jspのリンクから渡される)
        String subjectCd = request.getParameter("cd");

        // 科目コードが指定されていない場合のエラーハンドリング
        if (subjectCd == null || subjectCd.trim().isEmpty()) {
            request.setAttribute("errorMessage", "変更対象の科目コードが指定されていません。");
            // 科目一覧ページへ戻ることを想定 (SRJM002.jsp)
            RequestDispatcher dispatcher = request.getRequestDispatcher("SRJM002.jsp");
            dispatcher.forward(request, response);
            return;
        }

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = null;
		try {
			subject = subjectDao.get(subjectCd, school);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} // SubjectDaoを使って科目情報を取得

        // 取得した科目情報が存在しない場合のエラーハンドリング
        if (subject == null) {
            request.setAttribute("errorMessage", "指定された科目コードの科目が見つかりませんでした。");
            // 科目一覧ページへ戻ることを想定 (SRJM002.jsp)
            RequestDispatcher dispatcher = request.getRequestDispatcher("SRJM002.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 取得した科目情報をリクエストスコープにセットし、JSPにフォワード
        request.setAttribute("subject", subject);
        RequestDispatcher dispatcher = request.getRequestDispatcher("SBJM004.jsp");
        dispatcher.forward(request, response);
    }

    // 科目変更の画面表示は通常GETリクエストで行われるため、doPostはここでは実装しません。
    // もしPOSTでこのサーブレットが呼ばれるシナリオがある場合は、doPostメソッドを追加してください。
}