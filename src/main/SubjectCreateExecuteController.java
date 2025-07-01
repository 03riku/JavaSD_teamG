package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Subject;
import dao.SubjectDao;

@WebServlet("/SBJM003")
public class SubjectCreateExecuteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("=====================================================================");

        request.setCharacterEncoding("UTF-8");

        // リクエストパラメータの取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");


        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);

        // セッションから教師情報を取得し、その教師が所属する学校を学生に設定
        HttpSession session = request.getSession();
        System.out.println("session:" + session);
        Subject getTeacher = (Subject) session.getAttribute("subject"); // 変数名をGetteacherからgetTeacherに修正
        System.out.println("===============================================");

        // ★★★ ここでセッションから取得したTeacherオブジェクトのSchoolを設定します ★★★
        if (getTeacher != null && getTeacher.getSchool() != null) {
            subject.setSchool(getTeacher.getSchool());
        } else {
            // 教師情報または学校情報がセッションにない場合の処理
            System.out.println("===== errorMessage:セッションに教師または学校情報がありません。");
            request.setAttribute("errorMessage", "セッション情報に問題が発生しました。再度ログインしてください。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        SubjectDao subjectDao = new SubjectDao();
        try {
            // 学生情報のDB登録

            // 成功メッセージの設定とフォワード
            request.setAttribute("message", "学生情報が正常に登録されました。");
            request.getRequestDispatcher("/log/STDM003.jsp").forward(request, response);
        } catch (Exception e) {
            // エラーハンドリング
            e.printStackTrace(); // エラーの詳細をコンソールに出力
            request.setAttribute("errorMessage", "データベースエラーが発生しました：" + e.getMessage());
            request.getRequestDispatcher("/log/ERRO001.jsp").forward(request, response);
            return;
        }
    }
}