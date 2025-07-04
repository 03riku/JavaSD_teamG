package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.School; // School Beanをインポート
import Bean.Student;
import Bean.Subject;
import Bean.Test;

public class TestDao extends Dao {

    // 科目情報でフィルタリングするメソッド (引数にschoolCdを追加)
    public List<Test> filter(Integer entYear, String classNum, String subjectCd, String schoolCd) throws Exception {
        List<Test> tests = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT test.student_no, student.name, student.ent_year, student.class_num, test.subject_cd, subject.name AS subject_name, test.no, test.point ");
            sql.append("FROM test ");
            sql.append("JOIN student ON test.student_no = student.no AND student.school_cd = ? "); // 学校コードで結合
            sql.append("JOIN subject ON test.subject_cd = subject.cd AND subject.school_cd = ? "); // 学校コードで結合
            sql.append("WHERE 1=1 "); // 後続の条件のためにダミー条件

            List<Object> params = new ArrayList<>();
            params.add(schoolCd);
            params.add(schoolCd); // subjectも同じ学校コード

            if (entYear != null) {
                sql.append("AND student.ent_year = ? ");
                params.add(entYear);
            }
            if (classNum != null && !classNum.isEmpty()) {
                sql.append("AND student.class_num = ? ");
                params.add(classNum);
            }
            if (subjectCd != null && !subjectCd.isEmpty()) {
                sql.append("AND test.subject_cd = ? ");
                params.add(subjectCd);
            }

            sql.append("ORDER BY student.no, test.no");

            st = con.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Test test = new Test();
                Student student = new Student();
                Subject subject = new Subject();

                student.setNo(rs.getString("student_no"));
                student.setName(rs.getString("name"));
                student.setEntYear(rs.getInt("ent_year"));
                student.setClassNum(rs.getString("class_num"));

                subject.setCd(rs.getString("subject_cd"));
                subject.setName(rs.getString("subject_name"));

                test.setStudent(student);
                test.setSubject(subject);
                test.setNo(rs.getInt("no")); // 試験回数
                test.setPoint(rs.getInt("point"));

                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラーが発生しました。", e);
        } finally {
            if (st != null) try { st.close(); } catch (SQLException e) { /* ignore */ }
            if (con != null) try { con.close(); } catch (SQLException e) { /* ignore */ }
        }
        return tests;
    }

    // ★学生番号でフィルタリングする新しいメソッド (引数にschoolCdを追加)★
    public List<Test> filterByStudentId(String studentNo, String schoolCd) throws Exception {
        List<Test> tests = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = getConnection();
            String sql = "SELECT test.student_no, student.name, student.ent_year, student.class_num, test.subject_cd, subject.name AS subject_name, test.no, test.point " +
                         "FROM test " +
                         "JOIN student ON test.student_no = student.no AND student.school_cd = ? " + // 学校コードで結合
                         "JOIN subject ON test.subject_cd = subject.cd AND subject.school_cd = ? " + // 学校コードで結合
                         "WHERE test.student_no = ? ORDER BY test.subject_cd, test.no";
            st = con.prepareStatement(sql);
            st.setString(1, schoolCd);
            st.setString(2, schoolCd);
            st.setString(3, studentNo);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Test test = new Test();
                Student student = new Student();
                Subject subject = new Subject();

                student.setNo(rs.getString("student_no"));
                student.setName(rs.getString("name"));
                student.setEntYear(rs.getInt("ent_year"));
                student.setClassNum(rs.getString("class_num"));

                subject.setCd(rs.getString("subject_cd"));
                subject.setName(rs.getString("subject_name"));

                test.setStudent(student);
                test.setSubject(subject);
                test.setNo(rs.getInt("no"));
                test.setPoint(rs.getInt("point"));

                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラーが発生しました。", e);
        } finally {
            if (st != null) try { st.close(); } catch (SQLException e) { /* ignore */ }
            if (con != null) try { con.close(); } catch (SQLException e) { /* ignore */ }
        }
        return tests;
    }

    // 必要に応じて、StudentDaoやSubjectDaoにあるべきメソッドをここに移動したり、
    // TestDaoから呼び出すように修正してください。

    // StudentDaoに存在するはずのメソッドですが、参考としてTestDaoに仮で記述
    public List<Integer> getEntYears(School school) throws Exception {
        List<Integer> entYears = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            // 学校コードでフィルタリング
            st = con.prepareStatement("SELECT DISTINCT ent_year FROM student WHERE school_cd = ? ORDER BY ent_year");
            st.setString(1, school.getCd());
            rs = st.executeQuery();
            while (rs.next()) {
                entYears.add(rs.getInt("ent_year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("入学年度の取得中にエラーが発生しました。", e);
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (st != null) try { st.close(); } catch (SQLException e) { /* ignore */ }
            if (con != null) try { con.close(); } catch (SQLException e) { /* ignore */ }
        }
        return entYears;
    }

    // SubjectDaoに存在するはずのメソッドですが、参考としてTestDaoに仮で記述
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement("SELECT * FROM subject WHERE cd = ? AND school_cd = ?");
            st.setString(1, cd);
            st.setString(2, school.getCd());
            rs = st.executeQuery();
            if (rs.next()) {
                subject = new Subject();
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                subject.setSchool(school); // Schoolオブジェクトもセット
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("科目の取得中にエラーが発生しました。", e);
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (st != null) try { st.close(); } catch (SQLException e) { /* ignore */ }
            if (con != null) try { con.close(); } catch (SQLException e) { /* ignore */ }
        }
        return subject;
    }
}