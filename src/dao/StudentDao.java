package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Student;

public class StudentDao extends Dao {

    private String basesql;

    public StudentDao() {
        // Student テーブルの基本 SELECT 文
        this.basesql = "SELECT s.no, s.name, s.ent_year, s.class_num, s.attend, s.school_cd FROM Student s";
    }

    // 学生を no で取得
    public Student get(String no) throws Exception {
        String sql = basesql + " WHERE s.no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    School school = new School();
                    school.setCd(rs.getString("school_cd"));
                    return postFilter(rs, school).get(0);
                }
                return null;
            }
        }
    }

    // ResultSet → Student オブジェクトへ変換
    private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
        List<Student> list = new ArrayList<>();
        do {
            Student s = new Student();
            s.setNo(rSet.getString("no"));
            s.setName(rSet.getString("name"));
            s.setEntYear(rSet.getInt("ent_year"));
            s.setClassNum(rSet.getString("class_num"));
            s.setAttend(rSet.getBoolean("attend"));
            s.setSchool(school);
            list.add(s);
        } while (rSet.next());
        return list;
    }

    // フィルタ検索（学校・入学年・クラス・出席状態）
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        String sql = basesql + " WHERE s.school_cd = ? AND s.ent_year = ? AND s.class_num = ? AND s.attend = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setBoolean(4, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                List<Student> result = new ArrayList<>();
                while (rs.next()) {
                    result.addAll(postFilter(rs, school));
                }
                return result;
            }
        }
    }

    // フィルタ検索（学校・入学年・出席状態）
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        return filter(school, entYear, null, isAttend);
    }

    // フィルタ検索（学校・出席状態）
    public List<Student> filter(School school, boolean isAttend) throws Exception {
        String sql = basesql + " WHERE s.school_cd = ? AND s.attend = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setBoolean(2, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                List<Student> result = new ArrayList<>();
                while (rs.next()) {
                    result.addAll(postFilter(rs, school));
                }
                return result;
            }
        }
    }

    // 学生の新規登録
    public void insert(Student student) throws Exception {
        String sql = "INSERT INTO Student (no, name, ent_year, class_num, attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getNo());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getEntYear());
            ps.setString(4, student.getClassNum());
            ps.setBoolean(5, student.isAttend());

            if (student.getSchool() != null) {
                ps.setString(6, student.getSchool().getCd());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }

            ps.executeUpdate();
        }
    }

    // 学生情報を更新
    public void update(Student student) throws Exception {
        String sql = "UPDATE Student SET name = ?, ent_year = ?, class_num = ?, attend = ?, school_cd = ? WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getEntYear());
            ps.setString(3, student.getClassNum());
            ps.setBoolean(4, student.isAttend());

            if (student.getSchool() != null) {
                ps.setString(5, student.getSchool().getCd());
            } else {
                ps.setNull(5, java.sql.Types.VARCHAR);
            }

            ps.setString(6, student.getNo());

            ps.executeUpdate();
        }
    }

    // 全件取得
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(basesql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                School school = new School();
                school.setCd(rs.getString("school_cd"));

                Student s = new Student();
                s.setNo(rs.getString("no"));
                s.setName(rs.getString("name"));
                s.setEntYear(rs.getInt("ent_year"));
                s.setClassNum(rs.getString("class_num"));
                s.setAttend(rs.getBoolean("attend"));
                s.setSchool(school);

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
