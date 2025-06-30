package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Teacher;
import dao.TeacherDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/log/MMNU001"})  // JSPのactionに合わせて修正
public class LoginExecuteController extends CommonServlet {
    private static final long serialVersionUID = 1L;

    protected void post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("debug-chk002");
    	request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        TeacherDao TeacherDao = new TeacherDao();
        Teacher teacher = null;

        try {
            // loginメソッドで認証（IDとパスワードを使ってチェック）
            teacher = TeacherDao.login(id, password);

            if (teacher == null) {
                // ログイン失敗：IDが存在しないか、パスワードが一致しない
                System.out.println("ログイン失敗: teacher == null");
                request.setAttribute("error", "IDまたはパスワードが正しくありません");
                request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
                return;
            }

            // ログイン成功 → セッションに保存してメニューへ
            HttpSession session = request.getSession();
            session.setAttribute("teacher", teacher);
            System.out.println("teacher.getSchool():"+teacher.getSchool());
            response.sendRedirect(request.getContextPath() + "/log/MMNU001.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            // 例外発生時はエラーページまたはログイン画面に遷移
            request.getRequestDispatcher("/log/LOGI001.jsp").forward(request, response);
        }
    }

    protected void get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
