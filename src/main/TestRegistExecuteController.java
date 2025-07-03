package main; // あなたのControllerがあるパッケージ名に合わせる

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.School;

@WebServlet("/RegisterGradesExecute.action") // ★これがないと404エラーになります★
public class TestRegistExecuteController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String schoolCd = "oom"; // TestListSubjectExecuteControllerと合わせる
        School currentSchool = new School();
        currentSchool.setCd(schoolCd);

        String message = ""; // ユーザーへのメッセージ

        try {
            // ここに、GRMU001.jsp から送られてくる成績データを取得し、
            // TestDao を使ってデータベースを更新するロジックを記述します。
            // (前回の回答で提示した「複数の学生の点数を一括更新する場合の処理例」を参考にしてください)

            // 例: (簡略化のため、ここではDB更新ロジックは詳細に記述せず、成功と仮定します)
            // String[] studentNos = request.getParameterValues("studentNo");
            // String[] points = request.getParameterValues("point");
            // ... (各学生の点数更新処理) ...

            // データベース更新が成功したと仮定
            boolean updateSuccess = true; // 実際のDB処理の結果を設定してください

            if (updateSuccess) {
                message = "登録が完了しました"; // 完了メッセージを設定
            } else {
                message = "登録に失敗しました。"; // 失敗メッセージを設定
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "システムエラーが発生しました：" + e.getMessage();
        }

        // 登録完了メッセージをリクエスト属性に設定 (JSP側で ${message} で表示できるように)
        request.setAttribute("message", message); // このJSPは固定メッセージなので、使わなくても問題ないですが、共通化を考えると設定しておくのが良いです。

        // ★★★ 目的の完了画面である GRMU002.jsp にフォワードする ★★★
        request.getRequestDispatcher("log/GRMU002.jsp").forward(request, response);
    }
}