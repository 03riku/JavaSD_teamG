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
@WebServlet(urlPatterns={"/main/subjectdelete_done"})
public class Subject_delete_doneServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Teacher teacher = (session != null) ? (Teacher) session.getAttribute("teacher") : null;

        try {
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        SubjectDao dao = new SubjectDao();

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());
        System.out.println("########################1");
		System.out.println(cd);
		System.out.println(name);



			boolean flag = dao.delete(subject);

			if (flag != false) {
				System.out.println("--------------------");
				System.out.println("ok");
				request.getRequestDispatcher("/log/SBJM007.jsp").forward(request, response);
			} else {
				//エラーの部分
				System.out.println("--------------------");
				System.out.println(cd);
				System.out.println(name);

				request.setAttribute("error", "error");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}







    }
}
