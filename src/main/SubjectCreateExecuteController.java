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
import dao.SubjectDao;

@WebServlet("/SBJM003")
public class SubjectCreateExecuteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("==== SubjectCreateExecuteController ====");

        request.setCharacterEncoding("UTF-8");

        // リクエストパラメータ取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // 入力バリデーション例（必須チェック）
        if (cd == null || cd.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "コード・名称は必須項目です。");
            request.getRequestDispatcher("/log/ERRO001.jsp").forward(request, response);
            return;
        }

        Subject subject = new Subject();
        subject.setCd(cd.trim());
        subject.setName(name.trim());

        // セッションからTeacherを取得
        HttpSession session = request.getSession(false);
        if (session == null) {
            showError(request, response, "セッションが有効ではありません。再度ログインしてください。");
            return;
        }

        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null || teacher.getSchool() == null) {
            showError(request, response, "セッションに教師情報または学校情報が存在しません。");
            return;
        }

        // school_cd を SUBJECT OBJECTにセット
        subject.setSchool(teacher.getSchool());

        // 登録
        SubjectDao dao = new SubjectDao();
        try {
            boolean ok = dao.save(subject);
            if (!ok) {
                showError(request, response, "科目の登録または更新に失敗しました。");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError(request, response, "データベースエラーが発生しました：" + e.getMessage());
            return;
        }

        // 登録成功：リダイレクト（PRGパターン）
        response.sendRedirect(request.getContextPath() + "/log/SBJM003.jsp");
    }

    private void showError(HttpServletRequest req, HttpServletResponse res, String msg)
            throws ServletException, IOException {
        req.setAttribute("errorMessage", msg);
        req.getRequestDispatcher("/log/ERRO001.jsp").forward(req, res);
    }
}
