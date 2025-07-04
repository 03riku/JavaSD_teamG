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
import Bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;

@WebServlet("/TestListStudentExecute.action")
public class Test_list_studentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String schoolCd = "oom"; // 仮で固定。本番ではセッションから取得するべきです。
        School currentSchool = new School();
        currentSchool.setCd(schoolCd);

        // DAOのインスタンス化
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();

        // ドロップダウンリスト用のデータを格納するリスト
        List<Integer> entYears = new ArrayList<>();
        List<String> classNums = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        // List<Integer> numList = new ArrayList<>(); // テスト回数用 (JSPで使用しないため削除)

        // 検索結果を格納するリストとメッセージ
        List<Test> testResults = new ArrayList<>();
        String message = "";
        String searchType = "initial"; // 検索タイプを初期化: "initial" (初期表示), "bySubject" (科目検索), "byStudentId" (学生ID検索)

        try {
            // --- ドロップダウンリストデータの取得 (常に必要) ---
            entYears = studentDao.getEntYears(currentSchool);
            request.setAttribute("entYears", entYears);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): entYears list size: " + entYears.size());

            classNums = classNumDao.filter(schoolCd);
            request.setAttribute("classNums", classNums);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): classNums list size: " + classNums.size());

            subjects = subjectDao.filter(currentSchool);
            request.setAttribute("subjects", subjects);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): subjects list size: " + subjects.size());

            // テスト回数リスト (JSPで使用しないため削除)
            // numList.add(1);
            // numList.add(2);
            // request.setAttribute("numSet", numList);


            // --- リクエストパラメータの取得 ---
            String paramYearStr = request.getParameter("year");
            String paramClassNum = request.getParameter("class_num");
            String paramSubjectCd = request.getParameter("subject");
            String paramStudentId = request.getParameter("studentId");

            // どの検索ボタンが押されたかを判断するパラメータ
            String searchSubjectButton = request.getParameter("searchSubject"); // 科目情報検索ボタンのname属性
            String searchStudentButton = request.getParameter("searchStudent"); // 学生情報検索ボタンのname属性


            // 検索パラメータをJSPに送り返し、選択状態を維持する
            request.setAttribute("paramYear", paramYearStr);
            request.setAttribute("paramClassNum", paramClassNum);
            request.setAttribute("paramSubjectCd", paramSubjectCd);
            request.setAttribute("paramStudentId", paramStudentId);


            // デバッグ出力 - 取得したパラメータを確認
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Retrieved Parameters:");
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):     year: " + paramYearStr);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):     class_num: " + paramClassNum);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):     subject: " + paramSubjectCd);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):     studentId: " + paramStudentId);
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):     searchSubjectButton pressed: " + (searchSubjectButton != null));
            System.out.println("DEBUG (" + this.getClass().getSimpleName() + "):     searchStudentButton pressed: " + (searchStudentButton != null));


            // ★ここから検索ロジックの修正★
            // どちらかの検索ボタンが明示的に押されたかをチェック
            if (searchStudentButton != null) { // 学生情報の検索ボタンが押された場合
                if (paramStudentId != null && !paramStudentId.isEmpty()) {
                    // 学生番号が入力されている場合
                    System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Searching by student ID: " + paramStudentId);
                    testResults = testDao.filterByStudentId(paramStudentId, schoolCd); // 学生IDでの検索メソッドを呼び出し
                    searchType = "byStudentId"; // 検索タイプを設定

                    if (testResults.isEmpty()) {
                        message = "該当する学生の成績データが見つかりませんでした。";
                    } else {
                        // 学生ID検索の場合、学生情報（学生番号と氏名）のみを表示したいので、メッセージを調整
                        message = "検索結果が見つかりました。";
                    }
                } else {
                    // 学生番号が空の場合
                    message = "学生番号を入力してください。"; // 具体的なメッセージ
                    testResults = new ArrayList<>(); // 結果を空にする
                    searchType = "initial"; // エラー状態を示すタイプ
                    System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Student ID is empty when searchStudentButton was pressed.");
                }
            } else if (searchSubjectButton != null) { // 科目情報の検索ボタンが押された場合
                if ((paramYearStr != null && !paramYearStr.isEmpty()) &&
                    (paramClassNum != null && !paramClassNum.isEmpty()) &&
                    (paramSubjectCd != null && !paramSubjectCd.isEmpty())) {
                    // 入学年度、クラス、科目が全て選択されている場合
                    System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Searching by year, class, subject.");
                    Integer year = Integer.parseInt(paramYearStr);
                    testResults = testDao.filter(year, paramClassNum, paramSubjectCd, schoolCd); // 科目条件での検索メソッドを呼び出し
                    searchType = "bySubject"; // 検索タイプを設定

                    if (testResults.isEmpty()) {
                        message = "指定された科目条件の成績は見つかりませんでした。";
                    } else {
                        message = "検索結果が見つかりました。";
                    }
                } else {
                    // 入学年度、クラス、科目のいずれか（または複数）が空の場合
                    message = "入学年度、クラス、科目をすべて選択してください。"; // 具体的なメッセージ
                    testResults = new ArrayList<>(); // 結果を空にする
                    searchType = "initial"; // エラー状態を示すタイプ
                    System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Incomplete subject search parameters when searchSubjectButton was pressed.");
                }
            } else {
                // どちらの検索ボタンも押されずに初回表示された場合、または不正なリクエストの場合
                message = "科目情報を選択または学生情報を入力して検索ボタンをクリックしてください";
                testResults = new ArrayList<>(); // 結果を空にする
                searchType = "initial";
                System.out.println("DEBUG (" + this.getClass().getSimpleName() + "): Initial load or no specific search triggered.");
            }
            // ★ここまで検索ロジックの修正★

            // 検索結果とメッセージ、検索タイプをJSPに設定
            request.setAttribute("testResults", testResults);
            request.setAttribute("message", message);
            request.setAttribute("searchType", searchType); // searchTypeをセット

            request.getRequestDispatcher("/log/GRMR001.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // paramYearStr が数値に変換できない場合のエラーハンドリング
            e.printStackTrace();
            request.setAttribute("message", "不正な入学年度が入力されました。");
            request.setAttribute("testResults", new ArrayList<>()); // 結果を空にする
            request.setAttribute("searchType", "initial"); // エラー時は初期状態に戻す
            System.err.println("ERROR (" + this.getClass().getSimpleName() + "): NumberFormatException for year parameter: " + e.getMessage());
            request.getRequestDispatcher("/log/GRMR001.jsp").forward(request, response); // エラーメッセージを表示して元のJSPに戻す
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "成績参照中にエラーが発生しました。詳細はシステム管理者にお問い合わせください。");
            request.setAttribute("testResults", new ArrayList<>()); // 結果を空にする
            request.setAttribute("searchType", "initial"); // エラー時は初期状態に戻す
            System.err.println("ERROR (" + this.getClass().getSimpleName() + "): An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}