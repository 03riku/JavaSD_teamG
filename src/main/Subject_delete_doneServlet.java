package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Subject;
import Bean.Teacher;
import dao.SubjectDao;

public class Subject_delete_doneServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Teacher teacher = (session != null) ? (Teacher) session.getAttribute("teacher") : null;
        if (teacher == null || teacher.getSchool() == null) {
            request.setAttribute("errorMessage", "ログインが必要です。");
            request.getRequestDispatcher("LOGO001.jsp").forward(request, response);
            return;
        }

        try {
        String cd = request.getParameter("no");
        String name = request.getParameter("name");

        SubjectDao dao = new SubjectDao();

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);

			boolean flag = dao.delete(subject);

			if (flag != false) {
				request.getRequestDispatcher("SBJM007.jsp").forward(request, response);
			} else {
				//エラーの部分
				request.setAttribute("error", "error");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}







    }
}
