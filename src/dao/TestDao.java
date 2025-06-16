package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Student;
import Bean.Subject;
import Bean.Test;

public class TestDao extends Dao {

    /**
     * [private] PKを指定してTestを1件取得する。
     */
    private Test get(Student student, Subject subject, School school, int no, Connection connection) throws Exception {
        Test test = null;
        String sql = "SELECT * FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, student.getNo());
            st.setString(2, subject.getCd());
            st.setString(3, school.getCd());
            st.setInt(4, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    test = new Test();
                    test.setStudent(student.getNo()); // ← 文字列として渡す
                    test.setSubject(subject.getCd()); // ← 文字列として渡す
                    test.setSchool(school);
                    test.setNo(no);
                    test.setPoint(rs.getInt("POINT"));
                    test.setClassNum(rs.getString("CLASS_NUM"));
                }
            }
        }
        return test;
    }

    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        try (Connection con = getConnection()) {
            return get(student, subject, school, no, con);
        }
    }

    private List<Test> postFilter(ResultSet rs, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Test test = new Test();
                test.setSchool(school);
                test.setStudent(rs.getString("STUDENT_NO"));
                test.setSubject(rs.getString("SUBJECT_CD"));
                test.setNo(rs.getInt("NO"));
                test.setPoint(rs.getInt("POINT"));
                test.setClassNum(rs.getString("CLASS_NUM"));
                list.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        String sql = "SELECT t.* FROM TEST t JOIN STUDENT s ON t.STUDENT_NO = s.NO " +
                     "WHERE s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND t.SUBJECT_CD = ? AND t.NO = ? AND t.SCHOOL_CD = ? " +
                     "ORDER BY t.STUDENT_NO";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, entYear);
            st.setString(2, classNum);
            st.setString(3, subject.getCd());
            st.setInt(4, no);
            st.setString(5, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                list = postFilter(rs, school);
            }
        }
        return list;
    }

    public boolean save(List<Test> list) throws Exception {
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Test test : list) {
                    save(test, con);
                }
                con.commit();
                return true;
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        }
    }

    private boolean save(Test test, Connection connection) throws Exception {
        // 必要なStudentとSubject情報を取得
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();

        Student student = studentDao.get(test.getStudent());
        Subject subject = subjectDao.get(test.getSubject(), test.getSchool());

        Test old = get(student, subject, test.getSchool(), test.getNo(), connection);
        int count = 0;

        if (old == null) {
            String sql = "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setString(1, test.getStudent());
                st.setString(2, test.getSubject());
                st.setString(3, test.getSchool().getCd());
                st.setInt(4, test.getNo());
                st.setInt(5, test.getPoint());
                st.setString(6, test.getClassNum());
                count = st.executeUpdate();
            }
        } else {
            String sql = "UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, test.getPoint());
                st.setString(2, test.getStudent());
                st.setString(3, test.getSubject());
                st.setString(4, test.getSchool().getCd());
                st.setInt(5, test.getNo());
                count = st.executeUpdate();
            }
        }
        return count > 0;
    }

    public boolean delete(List<Test> list) throws Exception {
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Test test : list) {
                    delete(test, con);
                }
                con.commit();
                return true;
            } catch (Exception e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean delete(Test test, Connection connection) throws Exception {
        String sql = "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";
        int count = 0;
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, test.getStudent());
            st.setString(2, test.getSubject());
            st.setString(3, test.getSchool().getCd());
            st.setInt(4, test.getNo());
            count = st.executeUpdate();
        }
        return count > 0;
    }
}
