package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.School;
import Bean.Subject;
import dao.ClassNumDao; // ClassNumDaoをインポート
import dao.StudentDao;
import dao.SubjectDao;

// ★★★ ここをあなたのサーブレットのURLパターンとして設定します ★★★
// 例えば、以前提案した "/GradeReference.action" や "/TestListStudentExecute.action" など
@WebServlet("/TestListStudentExecute.action") // 仮のURLパターン
public class Test_list_studentServlet extends HttpServlet { // もしクラス名をリファクタリングするならここも変更
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        // ★学校コードの取得方法は、あなたのシステムに合わせる必要があります。
        // ★例: ログイン時にセッションに保存された CurrentSchool オブジェクトから取得
        String schoolCd = "oom"; // 現状合わせるために仮で固定。本番ではセッションから取得するべきです。
        School currentSchool = new School();
        currentSchool.setCd(schoolCd);

        // DAOのインスタンス化
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();

        // ドロップダウンリスト用のデータを格納するリスト
        List<Integer> entYears = new ArrayList<>();
        List<String> classNums = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        List<Integer> numList = new ArrayList<>(); // テスト回数用

        try {
            // 入学年度のリストを取得
            // StudentDaoに getEntYears(School school) メソッドがあると仮定
            // もし引数がString schoolCdの場合は、studentDao.getEntYears(schoolCd); に変更
            entYears = studentDao.getEntYears(currentSchool);
            request.setAttribute("entYearSet", entYears);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): entYears list size: " + entYears.size());

            // クラス番号のリストを取得
            // ClassNumDaoに filter(String schoolCd) メソッドがあると仮定
            classNums = classNumDao.filter(schoolCd);
            request.setAttribute("classNumSet", classNums);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): classNums list size: " + classNums.size());

            // 科目のリストを取得
            // SubjectDaoに filter(School school) メソッドがあると仮定
            // もし引数がString schoolCdの場合は、subjectDao.filter(schoolCd); に変更
            subjects = subjectDao.filter(currentSchool);
            request.setAttribute("subjectSet", subjects);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): subjects list size: " + subjects.size());

            // テスト回数 (num) のリストを固定値で設定
            numList.add(1);
            numList.add(2);
            request.setAttribute("numSet", numList);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): numList size: " + numList.size());

            // 取得したデータをGRMR001.jspにフォワード
            request.getRequestDispatcher("/log/GRMR001.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "成績参照画面の初期データ取得中にエラーが発生しました。");
            System.err.println("ERROR (" + this.getClass().getSimpleName() + "): " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response); // エラーページに飛ばす
        }
    }

    // 必要に応じてdoPostメソッドも追加しますが、初期表示はdoGetが適切です。
}