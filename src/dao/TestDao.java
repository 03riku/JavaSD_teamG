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
     * [private] PKを指定してTestを1件取得する。privateなsave/deleteから利用される。
     */
	// privateなgetメソッドは、schoolも引数で受け取るように変更
    private Test get(Student student, Subject subject, School school, int no, Connection connection) throws Exception { // ★修正点1
        Test test = null;
        String sql = "SELECT * FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, student.getNo());
            st.setString(2, subject.getCd());
            st.setString(3, school.getCd()); // ★修正点2: 引数のschoolを直接使う
            st.setInt(4, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    test = new Test();
                    test.setStudent(student);
                    test.setSubject(subject);
                    test.setSchool(school);
                    test.setNo(no);
                    test.setPoint(rs.getInt("POINT"));
                    test.setClassNum(rs.getString("CLASS_NUM"));
                }
            }
        }
        return test;
    }

    // publicなgetメソッドは変更なし
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        try (Connection con = getConnection()) {
            return get(student, subject, school, no, con);
        }
    }
    /**
     * [private] ResultSetをTestリストに変換するヘルパーメソッド
     */
    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();

        try {
            while (rSet.next()) {
                Test test = new Test();
                test.setSchool(school);
                // 学生、科目は各DAOから取得してセット
                test.setStudent(studentDao.get(rSet.getString("student_no")));
                test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                test.setClassNum(rSet.getString("class_num"));
                list.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    /**
     * [検索] 学生の所属情報と科目、回数を指定して成績データを検索する
     */
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

    /**
     * [登録/更新] 複数の成績データをまとめて保存する（トランザクション処理）
     */
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
                throw e; // エラーを再スローして呼び出し元に伝える
            }
        }
    }

    /**
     * [private] 1件の成績データを保存するヘルパーメソッド
     */
    private boolean save(Test test, Connection connection) throws Exception {
        // 既存データをチェックするためにgetを呼び出す
        // ★修正点3: testオブジェクトが持つschool情報をgetに渡す
        Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo(), connection);
        int count = 0;

        if (old == null) {
            // INSERT
            String sql = "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setString(1, test.getStudent().getNo());
                st.setString(2, test.getSubject().getCd());
                st.setString(3, test.getSchool().getCd());
                st.setInt(4, test.getNo());
                st.setInt(5, test.getPoint());
                st.setString(6, test.getStudent().getClassNum()); // getStudent()のClassNumはControllerでセットされていないので注意
                count = st.executeUpdate();
            }
        } else {
            // UPDATE
            String sql = "UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, test.getPoint());
                st.setString(2, test.getStudent().getNo());
                st.setString(3, test.getSubject().getCd());
                st.setString(4, test.getSchool().getCd());
                st.setInt(5, test.getNo());
                count = st.executeUpdate();
            }
        }
        return count > 0;
    }
    /**
     * [削除] 複数の成績データをまとめて削除する（トランザクション処理）
     */
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

    /**
     * [private] 1件の成績データを削除するヘルパーメソッド
     */
    private boolean delete(Test test, Connection connection) throws Exception {
        String sql = "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";
        int count = 0;
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, test.getStudent().getNo());
            st.setString(2, test.getSubject().getCd());
            st.setString(3, test.getSchool().getCd());
            st.setInt(4, test.getNo());
            count = st.executeUpdate();
        }
        return count > 0;
    }
}