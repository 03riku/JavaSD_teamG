// src/main/java/servlet/SubjectUpdateExecuteServlet.java
package main;

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
import Bean.Teacher; // Teacher Beanもセッションから取得すると仮定
import dao.SubjectDao;

@WebServlet("/SBJM004") // SBJM004.jspのform actionと一致させる
public class Subject_updateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // リクエストのエンコーディングを設定

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher"); // セッションから先生情報を取得

        // ログインしていない、または先生情報がない場合はエラー
        if (teacher == null || teacher.getSchool() == null) {
            request.setAttribute("errorMessage", "ログインが必要です。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("LOGO001.jsp"); // ログインページへリダイレクト
            dispatcher.forward(request, response);
            return;
        }

        School school = teacher.getSchool(); // 先生が所属する学校を取得

        // パラメータの取得
        String subjectCd = request.getParameter("cd");   // 科目コード (hiddenで送信)
        String subjectName = request.getParameter("name"); // 科目名

        // 入力値の検証
        if (subjectName == null || subjectName.trim().isEmpty()) {
            // 科目名が未入力の場合
            request.setAttribute("errorMessage", "科目名が入力されていません。");
            // 現在の科目情報を取得し、JSPに再表示するためにセット
            SubjectDao subjectDao = new SubjectDao();
            Subject currentSubject = subjectDao.get(subjectCd, school);
            request.setAttribute("subject", currentSubject); // フォームに元の値をセット
            RequestDispatcher dispatcher = request.getRequestDispatcher("SBJM004.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Subject subject = new Subject();
        subject.setCd(subjectCd);
        subject.setName(subjectName);
        subject.setSchool(school); // 科目オブジェクトに学校情報をセット

        SubjectDao subjectDao = new SubjectDao();
        boolean isUpdated = subjectDao.save(subject); // saveメソッドで更新

        if (isUpdated) {
            // 更新成功
            request.setAttribute("successMessage", "科目の情報を更新しました。");
        } else {
            // 更新失敗
            request.setAttribute("errorMessage", "科目の更新に失敗しました。データベースエラーの可能性があります。");
        }

        // 更新後、再度SBJM004.jspを表示して結果をユーザーに通知
        // 更新後の情報を取得し直して表示 (必須ではないが、更新されたことを確認できる)
        Subject updatedSubject = subjectDao.get(subjectCd, school);
        request.setAttribute("subject", updatedSubject);

        RequestDispatcher dispatcher = request.getRequestDispatcher("SBJM004.jsp");
        dispatcher.forward(request, response);
    }
}