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
        this.basesql = "SELECT s.no, s.name, s.ent_year, s.class_num, s.attend, s.school_cd" +
                       " FROM Student s";
    }

    /**
     * 学生を no で取得
     */
    public Student get(String no) throws Exception {
        String sql = basesql + " WHERE s.no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    // School 情報を取得するため DAO or bean で取得すると想定
                    School school = new School();
                    school.setCd(rs.getString("school_cd"));
                    // school.setName(…); // 必要なら追加の DB クエリ

                    return postFilter(rs, school).get(0);
                }
                return null;
            }
        }
    }

    /**
     * ResultSet -> Student オブジェクトへ変換
     */
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

    /**
     * フィルタ検索（学校・入学年・クラス・出席状態）
     */
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

    /**
     * filter メソッド（学校・入学年・出席状態）
     */
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        return filter(school, entYear, null, isAttend);
    }

    /**
     * filter メソッド（学校・出席状態）
     */
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

    /**
     * 新規学生登録（INSERT文）
     */
    public void insert(Student student) throws Exception {
        String sql = "INSERT INTO Student (no, name, ent_year, class_num, attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getNo());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getEntYear());
            ps.setString(4, student.getClassNum());
            ps.setBoolean(5, student.isAttend()); // デフォルト値を設定していないならfalseなどをセット
            if (student.getSchool() != null) {
                ps.setString(6, student.getSchool().getCd());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }

            ps.executeUpdate();
        }
    }

    public List<Student> findAll() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
}
