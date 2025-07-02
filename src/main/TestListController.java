package main; // パッケージ名を変更

import Bean.Student;
import Bean.Subject;
import Bean.School;
import Bean.Test;

// daoパッケージからインポート
import dao.SubjectDao;
import dao.TestListSubjectDao;
import dao.TestListStudentDao;
import dao.StudentDao;

// ... (その他のインポート)
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestList.action")
public class TestListController extends HttpServlet {

}