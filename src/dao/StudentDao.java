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

    // ★★ ここから追加するメソッド ★★

    // 例: 全ての入学年度を取得するメソッド
    public List<Integer> getEntYears(School school) throws Exception {
        List<Integer> entYears = new ArrayList<>();
        // DISTINCTを使って重複しない入学年度を取得し、昇順に並べ替える
        String sql = "SELECT DISTINCT ENT_YEAR FROM STUDENT WHERE SCHOOL_CD = ? ORDER BY ENT_YEAR";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, school.getCd());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entYears.add(rs.getInt("ENT_YEAR"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーログ出力
            throw new Exception("入学年度の取得中にデータベースエラーが発生しました。", e); // 例外をラップして再スロー
        }
        return entYears;
    }

    // 例: 全てのクラス番号を取得するメソッド
    public List<String> getClassNums(School school) throws Exception {
        List<String> classNums = new ArrayList<>();
        // DISTINCTを使って重複しないクラス番号を取得し、昇順に並べ替える
        String sql = "SELECT DISTINCT CLASS_NUM FROM STUDENT WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, school.getCd());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    classNums.add(rs.getString("CLASS_NUM"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーログ出力
            throw new Exception("クラス番号の取得中にデータベースエラーが発生しました。", e); // 例外をラップして再スロー
        }
        return classNums;
    }

    // ★★ ここまで追加するメソッド ★★

    // 学生を no で取得
    public Student get(String no) throws Exception {
        String sql = basesql + " WHERE NO = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    School school = new School();
                    school.setCd(rs.getString("school_cd")); // ResultSetからschool_cdを取得してSchoolオブジェクトにセット
                    return postFilter(rs, school).get(0); // postFilterが現在の行からリストを生成するので、get(0)で取得
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
        // このメソッドは、rs.next()が呼び出された後のResultSetの現在の行から処理を開始します。
        // getメソッドでrs.next()が呼び出されているので、do-whileではなくwhileループが適切かもしれません。
        // ただし、getメソッドの呼び出し方によってはdo-whileでも機能します。
        // 一般的には、ResultSetの最初の行でこのメソッドが呼び出され、その後rs.next()で次の行へ進む、という流れになります。
        do {
            Student s = new Student();
            s.setNo(rSet.getString("no"));
            s.setName(rSet.getString("name"));
            s.setEntYear(rSet.getInt("ent_year"));
            s.setClassNum(rSet.getString("class_num"));
            s.setAttend("O".equals(rSet.getString("IS_ATTEND"))); // DBカラム名「IS_ATTEND」を使用し、'O'/'X'をbooleanに変換
            s.setSchool(school); // 引数で受け取ったSchoolオブジェクトをセット
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
            st.setBoolean(4, isAttend); // booleanを直接setBooleanでセット
            try (ResultSet rs = st.executeQuery()) {
                // postFilterはResultSetの現在の位置から処理を開始し、rs.next()を内部で呼び出すため
                // ここでrs.next()を呼び出す必要はありません。
                if (rs.next()) { // 最初の行があるか確認
                    return postFilter(rs, school);
                }
                return new ArrayList<>(); // 結果がない場合は空のリストを返す
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
            st.setBoolean(3, isAttend); // booleanを直接setBooleanでセット
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
            st.setBoolean(2, isAttend); // booleanを直接setBooleanでセット
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
            ps.setBoolean(5, student.isAttend()); // booleanを直接setBooleanでセット
            System.out.println("ps:"+ ps); // デバッグ用

            if (student.getSchool() != null && student.getSchool().getCd() != null) {
                ps.setString(6, student.getSchool().getCd());
            } else {
                throw new IllegalArgumentException("insert: SCHOOL_CD must not be null (student=" + student + ")");
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            ps.setBoolean(4, student.isAttend()); // booleanを直接setBooleanでセット

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
        // Schoolオブジェクトは各Studentに設定されるため、ループ内でインスタンス化が必要
        String sql = basesql + " ORDER BY ENT_YEAR, CLASS_NUM, NO"; // 並び順を指定
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                School school = new School(); // 各学生ごとにSchoolオブジェクトを生成
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