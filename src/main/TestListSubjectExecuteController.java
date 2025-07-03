package main;

import java.io.IOException;
import java.time.LocalDate;
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
import dao.TestListSubjectDao;

@WebServlet("/TestListSubjectExecute.action")
public class TestListSubjectExecuteController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String schoolCd = "oom";
        School currentSchool = new School();
        currentSchool.setCd(schoolCd);

        if (currentSchool.getCd() == null || currentSchool.getCd().isEmpty()) {
            request.setAttribute("message", "学校情報が見つかりません。ログインしてください。");
            request.getRequestDispatcher("log/login.jsp").forward(request, response);
            return;
        }

        String yearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");
        String numStr = request.getParameter("num");

        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        ClassNumDao classNumDao = new ClassNumDao();
        TestListSubjectDao testListSubjectDao = new TestListSubjectDao();

        List<Integer> entYearSet = new ArrayList<>();
        List<String> classNumSet = new ArrayList<>();
        List<Subject> subjectSet = new ArrayList<>();
        List<Integer> numSet = new ArrayList<>();

        try {
            // --- 入学年度のリストを生成 ---
            LocalDate todaysDate = LocalDate.now();
            int currentYear = todaysDate.getYear();
            for (int i = currentYear - 10; i <= currentYear + 1; i++) {
                entYearSet.add(i);
            }
            System.out.println("DEBUG: entYearSet size = " + entYearSet.size()); // ★デバッグログ★

            // --- クラス番号のリストをDBから取得 ---
            classNumSet = classNumDao.filter(currentSchool.getCd());
            System.out.println("DEBUG: classNumSet size = " + classNumSet.size()); // ★デバッグログ★
            if (classNumSet.isEmpty()) {
                System.out.println("DEBUG: classNumSet is empty! Check ClassNumDao and DB data.");
            } else {
                System.out.println("DEBUG: classNumSet contains: " + String.join(", ", classNumSet));
            }


            // --- 科目のリストをDBから取得 ---
            subjectSet = subjectDao.filter(currentSchool); // Schoolオブジェクトを渡す
            System.out.println("DEBUG: subjectSet size = " + subjectSet.size()); // ★デバッグログ★
            if (subjectSet.isEmpty()) {
                System.out.println("DEBUG: subjectSet is empty! Check SubjectDao and DB data.");
            } else {
                for (Subject sub : subjectSet) {
                    System.out.println("DEBUG: Subject: " + sub.getName() + " (" + sub.getCd() + ")");
                }
            }


            // --- 試験回数（num）のリストを固定で設定 ---
            numSet.add(1);
            numSet.add(2);
            System.out.println("DEBUG: numSet size = " + numSet.size()); // ★デバッグログ★

            // 各リストをリクエスト属性にセット
            request.setAttribute("entYearSet", entYearSet);
            request.setAttribute("classNumSet", classNumSet);
            request.setAttribute("subjectSet", subjectSet);
            request.setAttribute("numSet", numSet);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: Initial data fetching failed: " + e.getMessage()); // ★エラーログ★
            request.setAttribute("message", "初期データ取得中にエラーが発生しました：" + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        List<Test> tests = new ArrayList<>();
        String message = "";

        // 検索が実行された場合の処理 (POSTリクエスト時もここに来る)
        if (yearStr != null && !yearStr.isEmpty() &&
            classNum != null && !classNum.isEmpty() &&
            subjectCd != null && !subjectCd.isEmpty() &&
            numStr != null && !numStr.isEmpty()) {

            Integer entYear = null;
            Integer num = null;
            try {
                entYear = Integer.parseInt(yearStr);
                num = Integer.parseInt(numStr);
            } catch (NumberFormatException e) {
                message = "入学年度または回数が不正な値です。";
                request.setAttribute("message", message);
            }

            if (message.isEmpty()) {
                try {
                    Subject selectedSubject = subjectDao.get(subjectCd, currentSchool);

                    if (selectedSubject == null) {
                        message = "指定された科目が見つかりませんでした。";
                    } else {
                        tests = testListSubjectDao.filterForRegister(entYear, classNum, selectedSubject, num, currentSchool);

                        if (tests.isEmpty()) {
                            message = "該当する成績情報はありませんでした。";
                        } else {
                            message = "検索結果があります。";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("ERROR: Search execution failed: " + e.getMessage()); // ★エラーログ★
                    message = "データベース処理中にエラーが発生しました。エラーの詳細: " + e.getMessage();
                }
            }
        } else if (request.getParameterMap().containsKey("entYear") ||
                   request.getParameterMap().containsKey("classNum") ||
                   request.getParameterMap().containsKey("subjectCd") ||
                   request.getParameterMap().containsKey("num")) {
            // 検索ボタンが押されたが、一部のパラメータが不足している場合
            message = "検索条件をすべて選択してください。";
        }


        request.setAttribute("tests", tests);
        request.setAttribute("message", message);

        request.setAttribute("selectedEntYear", yearStr);
        request.setAttribute("selectedClassNum", classNum);
        request.setAttribute("selectedSubjectCd", subjectCd);
        request.setAttribute("selectedNum", numStr);

        try {
            if (subjectCd != null && !subjectCd.isEmpty()) {
                Subject selectedSubjectForName = subjectDao.get(subjectCd, currentSchool);
                if (selectedSubjectForName != null) {
                    request.setAttribute("selectedSubjectName", selectedSubjectForName.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 最終的にJSPにフォワード
        request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // POSTリクエストもdoGetで処理
    }
}