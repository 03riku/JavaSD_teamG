package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Student;

public class StudentDao extends Dao {

    private String basesql;

    public StudentDao() {
        this.basesql = "SELECT NO,NAME,ENT_YEAR,CLASS_NUM,IS_ATTEND,SCHOOL_CD FROM STUDENT";
    }

    // 入学年度を取得するメソッド（特定の学校にフィルタリング）
    public List<Integer> getEntYears(School school) throws Exception {
        List<Integer> entYears = new ArrayList<>();
        // DISTINCTを使って重複しない入学年度を取得し、昇順に並べ替える
        // SCHOOL_CD でフィルタリングする
        String sql = "SELECT DISTINCT ENT_YEAR FROM STUDENT WHERE SCHOOL_CD = ? ORDER BY ENT_YEAR";

        System.out.println("DEBUG (StudentDao.getEntYears): SQL: " + sql);
        System.out.println("DEBUG (StudentDao.getEntYears): Parameter School CD: " + (school != null ? school.getCd() : "null"));

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // schoolCd をバインド
            ps.setString(1, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("DEBUG (StudentDao.getEntYears): Query executed successfully. Fetching results...");
                while (rs.next()) {
                    int year = rs.getInt("ENT_YEAR");
                    entYears.add(year);
                    System.out.println("DEBUG (StudentDao.getEntYears): Found ENT_YEAR: " + year);
                }
                System.out.println("DEBUG (StudentDao.getEntYears): Total years found: " + entYears.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR (StudentDao.getEntYears): SQLException occurred: " + e.getMessage());
            throw new Exception("入学年度の取得中にデータベースエラーが発生しました。", e);
        }
        return entYears;
    }

    // クラス番号を取得するメソッド（特定の学校にフィルタリング）
    public List<String> getClassNums(School school) throws Exception {
        List<String> classNums = new ArrayList<>();
        // DISTINCTを使って重複しないクラス番号を取得し、昇順に並べ替える
        // SCHOOL_CD でフィルタリングする
        String sql = "SELECT DISTINCT CLASS_NUM FROM STUDENT WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

        System.out.println("DEBUG (StudentDao.getClassNums): SQL: " + sql);
        System.out.println("DEBUG (StudentDao.getClassNums): Parameter School CD: " + (school != null ? school.getCd() : "null"));

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // schoolCd をバインド
            ps.setString(1, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("DEBUG (StudentDao.getClassNums): Query executed successfully. Fetching results...");
                while (rs.next()) {
                    String classNum = rs.getString("CLASS_NUM");
                    classNums.add(classNum);
                    System.out.println("DEBUG (StudentDao.getClassNums): Found CLASS_NUM: " + classNum);
                }
                System.out.println("DEBUG (StudentDao.getClassNums): Total class numbers found: " + classNums.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR (StudentDao.getClassNums): SQLException occurred: " + e.getMessage());
            throw new Exception("クラス番号の取得中にデータベースエラーが発生しました。", e);
        }
        return classNums;
    }

    // 学生を no で取得
    public Student get(String no) throws Exception {
        String sql = basesql + " WHERE NO = ?";
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("学生情報の取得中にデータベースエラーが発生しました。", e);
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
            s.setAttend("O".equals(rSet.getString("IS_ATTEND")));
            s.setSchool(school);
            list.add(s);
        } while (rSet.next());
        return list;
    }

    // フィルタ検索（学校・入学年・クラス・出席状態）
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        String sql = basesql + " WHERE SCHOOL_CD = ? AND ENT_YEAR = ? AND CLASS_NUM = ? AND IS_ATTEND = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setBoolean(4, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return postFilter(rs, school);
                }
                return new ArrayList<>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("学生フィルタリング中にデータベースエラーが発生しました。", e);
        }
    }

    // フィルタ検索（学校・入学年・出席状態）
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        String sql = basesql + " WHERE SCHOOL_CD = ? AND ENT_YEAR = ? AND IS_ATTEND = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setBoolean(3, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return postFilter(rs, school);
                }
                return new ArrayList<>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("学生フィルタリング中にデータベースエラーが発生しました。", e);
        }
    }

    // フィルタ検索（学校・出席状態）
    public List<Student> filter(School school, boolean isAttend) throws Exception {
        String sql = basesql + " WHERE SCHOOL_CD = ? AND IS_ATTEND = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setBoolean(2, isAttend);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return postFilter(rs, school);
                }
                return new ArrayList<>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("学生フィルタリング中にデータベースエラーが発生しました。", e);
        }
    }

    // 学生の新規登録
    public void insert(Student student) throws Exception {
        System.out.println("=====================================================================");
        String sql = "INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, student.getNo());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getEntYear());
            ps.setString(4, student.getClassNum());
            ps.setBoolean(5, student.isAttend());

            if (student.getSchool() != null && student.getSchool().getCd() != null) {
                ps.setString(6, student.getSchool().getCd());
                System.out.println("DEBUG (StudentDao.insert): ps (before executeUpdate): " + ps.toString());
            } else {
                throw new IllegalArgumentException("insert: SCHOOL_CD must not be null (student=" + student + ")");
            }

            ps.executeUpdate();
            System.out.println("DEBUG (StudentDao.insert): Insert successful.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR (StudentDao.insert): SQLException occurred: " + e.getMessage());
            throw new Exception("学生の新規登録中にデータベースエラーが発生しました。", e);
        }
    }

    // 学生情報を更新
    public void update(Student student) throws Exception {
        String sql = "UPDATE STUDENT SET NAME = ?, ENT_YEAR = ?, CLASS_NUM = ?, IS_ATTEND = ?, SCHOOL_CD = ? WHERE NO = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getEntYear());
            ps.setString(3, student.getClassNum());
            ps.setBoolean(4, student.isAttend());

            if (student.getSchool() != null && student.getSchool().getCd() != null) {
                ps.setString(5, student.getSchool().getCd());
            } else {
                throw new IllegalArgumentException("update: SCHOOL_CD must not be null (student=" + student + ")");
            }

            ps.setString(6, student.getNo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("学生情報の更新中にデータベースエラーが発生しました。", e);
        }
    }

    // 全件取得
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = basesql + " ORDER BY ENT_YEAR, CLASS_NUM, NO";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                School school = new School();
                school.setCd(rs.getString("school_cd"));

                Student s = new Student();
                s.setNo(rs.getString("no"));
                s.setName(rs.getString("name"));
                s.setEntYear(rs.getInt("ent_year"));
                s.setClassNum(rs.getString("class_num"));
                s.setAttend("O".equals(rs.getString("IS_ATTEND")));
                s.setSchool(school);

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}