package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.School; // Schoolクラスをインポート
import Bean.Student;
import dao.StudentDao;

@WebServlet("/student_update_done")
public class Student_update_doneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 文字エンコーディング（POST対策）
        request.setCharacterEncoding("UTF-8");

        // フォームからの値を取得
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String entYearStr = request.getParameter("ent_year"); // intに変換するので一時的にStringで取得
        String classNum = request.getParameter("class_num");
        String isAttendStr = request.getParameter("is_attend"); // is_attend（在学中）を取得
        String schoolCd = request.getParameter("school_cd"); // school_cdを取得 (hiddenなどで渡される想定)

        try {
            // int型の変換
            int entYear = 0;
            if (entYearStr != null && !entYearStr.isEmpty()) {
                entYear = Integer.parseInt(entYearStr);
            }

            // boolean型の変換
            // HTMLのチェックボックスは、チェックされていると "on" などが送られ、チェックされていないと何も送られない
            boolean isAttend = (isAttendStr != null && isAttendStr.equals("on"));

            // School オブジェクトの生成とセット
            School school = new School();
            // Schoolコードは、フォームからhiddenフィールドなどで渡されるか、セッションなどから取得する想定です。
            // ここではフォームから渡されることを前提とします。
            if (schoolCd == null || schoolCd.isEmpty()) {
                 // schoolCdが取得できない場合はエラーとする
                 throw new IllegalArgumentException("学校コードが未設定のため学生情報を更新できません。");
            }
            school.setCd(schoolCd);


            // Student オブジェクトにセット
            Student student = new Student();
            student.setNo(no);
            student.setName(name);
            student.setEntYear(entYear);
            student.setClassNum(classNum);
            student.setAttend(isAttend); // ★修正: isAttendをセット
            student.setSchool(school);   // ★修正: Schoolオブジェクトをセット

            // DAO を使って更新処理
            StudentDao dao = new StudentDao();
            dao.update(student);

            // 更新成功後に完了画面へ遷移
            request.getRequestDispatcher("/log/STDM005.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // 入学年度の変換エラー
            e.printStackTrace();
            request.setAttribute("error", "入学年度が不正な値です。");
            request.getRequestDispatcher("/log/ERRO001.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            // school_cdがnullの場合のエラー (StudentDao.update内でスローされる可能性、または上記で手動でスローした場合)
            e.printStackTrace();
            request.setAttribute("error", e.getMessage()); // DAOからスローされたメッセージまたは上記で設定したメッセージを表示
            request.getRequestDispatcher("/log/ERRO001.jsp").forward(request, response);
        } catch (Exception e) { // その他の予期せぬエラー
            e.printStackTrace();
            request.setAttribute("error", "学生情報の更新中に予期せぬエラーが発生しました。管理者に連絡してください。");
            request.getRequestDispatcher("/log/ERRO001.jsp").forward(request, response);
        }
    }
}