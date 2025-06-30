package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Student;
import Bean.Teacher;
import dao.StudentDao; // パッケージ名に注意

@WebServlet("/STDM003")
public class Student_create_doneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("=====================================================================");

        request.setCharacterEncoding("UTF-8");

        // リクエストパラメータの取得
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        // School schoolcd = request.getParameter("school_cd"); // この行は削除またはコメントアウトします

        // 入学年度の数値変換
        int entYear = 0;
        try {
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            System.out.println("===== errorMessage:入学年度が不正です。数値を入力してください。");
            request.setAttribute("errorMessage", "入学年度が不正です。数値を入力してください。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(true);
        // student.setSchool(schoolcd); // この行は削除またはコメントアウトします

        // セッションから教師情報を取得し、その教師が所属する学校を学生に設定
        HttpSession session = request.getSession();
        System.out.println("session:" + session);
        Teacher getTeacher = (Teacher) session.getAttribute("teacher"); // 変数名をGetteacherからgetTeacherに修正
        System.out.println("===============================================");
        System.out.println("getTeacher:" + getTeacher);
        System.out.println("getTeacher.getSchool():" +  getTeacher.getSchool());

        // ★★★ ここでセッションから取得したTeacherオブジェクトのSchoolを設定します ★★★
        if (getTeacher != null && getTeacher.getSchool() != null) {
            student.setSchool(getTeacher.getSchool());
        } else {
            // 教師情報または学校情報がセッションにない場合の処理
            System.out.println("===== errorMessage:セッションに教師または学校情報がありません。");
            request.setAttribute("errorMessage", "セッション情報に問題が発生しました。再度ログインしてください。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        StudentDao studentDao = new StudentDao();
        try {
            // 学生情報のDB登録
            studentDao.insert(student);

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
        // forward後は通常returnしますので、この下の行は不要です
        // request.getRequestDispatcher("/log/STDM003.jsp").forward(request, response);
    }
}