package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.School;
import Bean.Student;
import Bean.Subject;
import Bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;

@WebServlet("/RegisterGradesExecute.action")
public class TestRegistExecuteController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String schoolCd = "oom"; // TestListSubjectExecuteControllerと合わせる
        School currentSchool = new School();
        currentSchool.setCd(schoolCd);

        String message = ""; // ユーザーへのメッセージ

        TestListSubjectDao testDao = new TestListSubjectDao();
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();

        try {
            // GRMU001.jsp から送信される隠しフィールドから検索条件を取得
            String selectedEntYearStr = request.getParameter("selectedEntYear");
            String selectedClassNum = request.getParameter("selectedClassNum");
            String selectedSubjectCd = request.getParameter("selectedSubjectCd");
            String selectedNumStr = request.getParameter("selectedNum");

            int entYear = (selectedEntYearStr != null && !selectedEntYearStr.isEmpty()) ? Integer.parseInt(selectedEntYearStr) : 0;
            int num = (selectedNumStr != null && !selectedNumStr.isEmpty()) ? Integer.parseInt(selectedNumStr) : 0;

            // 必須パラメータのチェック
            if (entYear == 0 || selectedClassNum == null || selectedClassNum.isEmpty() || selectedSubjectCd == null || selectedSubjectCd.isEmpty() || num == 0) {
                message = "登録に必要な情報が不足しています。";
                request.setAttribute("message", message);
                request.setAttribute("selectedEntYear", entYear);
                request.setAttribute("selectedClassNum", selectedClassNum);
                request.setAttribute("selectedSubjectCd", selectedSubjectCd);
                request.setAttribute("selectedNum", num);
                request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
                return;
            }

            Subject selectedSubject = subjectDao.get(selectedSubjectCd, currentSchool);
            if (selectedSubject == null) {
                message = "指定された科目が存在しません。";
                request.setAttribute("message", message);
                request.setAttribute("selectedEntYear", entYear);
                request.setAttribute("selectedClassNum", selectedClassNum);
                request.setAttribute("selectedSubjectCd", selectedSubjectCd);
                request.setAttribute("selectedNum", num);
                request.getRequestDispatcher("log/GRMU001.jsp").forward(request, response);
                return;
            }

            boolean allUpdatesSuccessful = true;
            List<String> failedStudents = new ArrayList<>();

            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                if (paramName.startsWith("score_")) {
                    String studentNo = paramName.substring("score_".length());
                    String pointStr = request.getParameter(paramName);

                    if (pointStr == null || pointStr.trim().isEmpty()) {
                        continue;
                    }

                    int point = -1;
                    try {
                        point = Integer.parseInt(pointStr);
                        if (point < 0 || point > 100) {
                            message = "点数は0から100の範囲で入力してください。";
                            allUpdatesSuccessful = false;
                            failedStudents.add(studentNo + " (点数範囲外)");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        message = "点数が無効な形式です。";
                        allUpdatesSuccessful = false;
                        failedStudents.add(studentNo + " (無効な形式)");
                        continue;
                    }

                    Test test = new Test();
                    Student student = studentDao.get(studentNo);
                    if (student == null) {
                        System.err.println("学生番号 " + studentNo + " の学生が見つかりません。");
                        failedStudents.add(studentNo);
                        allUpdatesSuccessful = false;
                        continue;
                    }
                    test.setStudent(student);
                    test.setClassNum(selectedClassNum);
                    test.setSubject(selectedSubject);
                    test.setSchool(currentSchool);
                    test.setNo(num);
                    test.setPoint(point);

                    // DAOを呼び出して保存 (既存なら更新、新規なら挿入)
                    try {
                        // ★ここが修正された点です★
                        boolean saveResult = testDao.insert(test); // testDao.save(test) から変更
                        if (!saveResult) {
                            allUpdatesSuccessful = false;
                            failedStudents.add(studentNo + " (DB更新失敗)");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        allUpdatesSuccessful = false;
                        failedStudents.add(studentNo + " (DBエラー)");
                        message = "データベース更新中にエラーが発生しました: " + e.getMessage();
                        continue;
                    }
                }
            }

            if (allUpdatesSuccessful) {
                message = "登録が完了しました";
            } else {
                if (message.isEmpty()) {
                    message = "登録に一部失敗しました。";
                }
                if (!failedStudents.isEmpty()) {
                    message += " (失敗した学生番号: " + String.join(", ", failedStudents) + ")";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "システムエラーが発生しました：" + e.getMessage();
        }

        request.setAttribute("message", message);

        request.getRequestDispatcher("log/GRMU002.jsp").forward(request, response);
    }
}