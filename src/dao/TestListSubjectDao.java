package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // SQLExceptionをインポート
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Student;
import Bean.Subject;
import Bean.Test;

public class TestListSubjectDao extends Dao {

    // 既存の成績取得用SQL (TestListSubjectExecuteControllerの検索結果表示用)
    private final String baseSql =
        "SELECT t.student_no, s.name AS student_name, s.ent_year, s.class_num AS student_class_num, s.is_attend, s.school_cd AS student_school_cd, " +
        "t.class_num AS test_class_num, t.subject_cd, sub.name AS subject_name, sub.school_cd AS subject_school_cd, " +
        "t.no, t.point, t.school_cd AS test_school_cd " +
        "FROM test AS t " +
        "JOIN student AS s ON t.student_no = s.no AND t.school_cd = s.school_cd " +
        "JOIN subject AS sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd " +
        "WHERE s.ent_year = ? AND t.class_num = ? AND t.subject_cd = ? AND t.no = ? AND t.school_cd = ?";

    // 新規登録・更新の際の存在チェックと取得用SQL
    private final String getSql =
        "SELECT student_no, class_num, subject_cd, school_cd, no, point FROM test " +
        "WHERE student_no = ? AND subject_cd = ? AND no = ? AND school_cd = ?";

    // 成績が存在しない学生も含むリストを取得するためのSQL (GRMU001.jsp用)
    // studentテーブルとtestテーブルをLEFT JOINし、testテーブルに該当データがなくてもstudent情報を取得する
    private final String filterForRegisterSql =
        "SELECT s.no AS student_no, s.name AS student_name, s.ent_year, s.class_num AS student_class_num, s.is_attend, s.school_cd AS student_school_cd, " +
        "t.no AS test_no, t.point AS test_point, t.class_num AS test_class_num, t.subject_cd AS test_subject_cd, t.school_cd AS test_school_cd " +
        "FROM student AS s " +
        "LEFT JOIN test AS t ON s.no = t.student_no AND s.school_cd = t.school_cd AND t.subject_cd = ? AND t.no = ? " +
        "WHERE s.ent_year = ? AND s.class_num = ? AND s.school_cd = ? ORDER BY s.no";


    private final String insertSql =
        "INSERT INTO test (student_no, class_num, subject_cd, school_cd, no, point) VALUES (?, ?, ?, ?, ?, ?)";

    private final String updateSql =
        "UPDATE test SET point = ? WHERE student_no = ? AND class_num = ? AND subject_cd = ? AND school_cd = ? AND no = ?";

    // ResultSetからTestオブジェクトのリストを生成するヘルパーメソッド
    // GRMR001.jsp向けのTestオブジェクトを生成
    private List<Test> postFilter(ResultSet rset) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rset.next()) {
            Test test = new Test();

            Student student = new Student();
            student.setNo(rset.getString("student_no"));
            student.setName(rset.getString("student_name"));
            student.setEntYear(rset.getInt("ent_year"));
            student.setClassNum(rset.getString("student_class_num"));
            student.setAttend(rset.getBoolean("is_attend"));
            School studentSchool = new School();
            studentSchool.setCd(rset.getString("student_school_cd"));
            student.setSchool(studentSchool);
            test.setStudent(student);

            Subject subject = new Subject();
            subject.setCd(rset.getString("subject_cd"));
            subject.setName(rset.getString("subject_name"));
            School subjectSchool = new School();
            subjectSchool.setCd(rset.getString("subject_school_cd"));
            subject.setSchool(subjectSchool);
            test.setSubject(subject);

            test.setClassNum(rset.getString("test_class_num")); // TESTテーブルのクラス番号

            School testSchool = new School();
            testSchool.setCd(rset.getString("test_school_cd"));
            test.setSchool(testSchool);

            test.setNo(rset.getInt("no"));
            test.setPoint(rset.getInt("point")); // pointがNULLの場合も考慮するならInteger型
            list.add(test);
        }
        return list;
    }

    // ResultSetからTestオブジェクトのリストを生成するヘルパーメソッド
    // GRMU001.jsp向けのTestオブジェクトを生成 (成績がnullの場合も考慮)
    private List<Test> postFilterForRegister(ResultSet rset, Subject subjectParam, int numParam) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rset.next()) {
            Test test = new Test();

            // Student情報を設定
            Student student = new Student();
            student.setNo(rset.getString("student_no"));
            student.setName(rset.getString("student_name"));
            student.setEntYear(rset.getInt("ent_year"));
            student.setClassNum(rset.getString("student_class_num"));
            student.setAttend(rset.getBoolean("is_attend"));
            School studentSchool = new School();
            studentSchool.setCd(rset.getString("student_school_cd"));
            student.setSchool(studentSchool);
            test.setStudent(student);

            // Subject情報はパラメータから設定
            test.setSubject(subjectParam);

            // クラス番号と学校コードはStudentから取得できるが、Testにも設定
            test.setClassNum(student.getClassNum());
            test.setSchool(student.getSchool());

            // 試験回数はパラメータから設定
            test.setNo(numParam);

            // 点数はResultSetから取得 (NULLの可能性あり)
            // JDBCではNULLのIntは0として読み込まれるため、ResultSet.wasNull()で判定する必要がある
            int point = rset.getInt("test_point");
            if (rset.wasNull()) {
                test.setPoint(-1); // 成績がない場合は-1などの特別な値で表現
            } else {
                test.setPoint(point);
            }
            list.add(test);
        }
        // ★ここに閉じ括弧が不足していました★
        return list;
    }

    // 成績参照画面用のフィルターメソッド
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> result = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(baseSql)) {

            stmt.setInt(1, entYear);
            stmt.setString(2, classNum);
            stmt.setString(3, subject.getCd());
            stmt.setInt(4, num);
            stmt.setString(5, school.getCd());

            try (ResultSet rset = stmt.executeQuery()) {
                result = postFilter(rset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("成績参照データの取得中にデータベースエラーが発生しました。", e);
        }
        return result;
    }

    // 成績登録画面用のフィルターメソッド (成績がない学生も含む)
    public List<Test> filterForRegister(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> result = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(filterForRegisterSql)) {

            stmt.setString(1, subject.getCd());
            stmt.setInt(2, num);
            stmt.setInt(3, entYear);
            stmt.setString(4, classNum);
            stmt.setString(5, school.getCd());

            try (ResultSet rset = stmt.executeQuery()) {
                result = postFilterForRegister(rset, subject, num); // subjectとnumを渡す
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("成績登録用データの取得中にデータベースエラーが発生しました。", e);
        }
        return result;
    }

    // Testオブジェクトを学生番号、科目コード、試験回数で取得するメソッド
    public Test get(String studentNo, String subjectCd, int num, String schoolCd) throws Exception {
        Test test = null;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(getSql)) {

            stmt.setString(1, studentNo);
            stmt.setString(2, subjectCd);
            stmt.setInt(3, num);
            stmt.setString(4, schoolCd);

            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    test = new Test();
                    // Beanに設定するために必要な関連オブジェクトも取得して設定

                    // Studentオブジェクトの取得
                    StudentDao studentDao = new StudentDao(); // インスタンス化
                    Student student = studentDao.get(rset.getString("student_no")); //

                    // Schoolオブジェクトをまず作成し、学校コードを設定
                    School school = new School();
                    school.setCd(rset.getString("school_cd")); // ResultSetから取得したschool_cdを設定

                    // Subjectオブジェクトの取得に、上で作成したschoolオブジェクトを渡す
                    SubjectDao subjectDao = new SubjectDao(); // インスタンス化
                    Subject subject = subjectDao.get(rset.getString("subject_cd"), school); //

                    test.setStudent(student);
                    test.setClassNum(rset.getString("class_num"));
                    test.setSubject(subject);
                    test.setSchool(school); // 正しいschoolオブジェクトをセット
                    test.setNo(rset.getInt("no"));
                    test.setPoint(rset.getInt("point"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("成績データの取得中にデータベースエラーが発生しました。", e);
        }
        return test;
    }

    /**
     * 新規のテストレコードをデータベースに挿入する。
     * @param test 挿入するデータを含むTestオブジェクト。
     * @return 挿入が成功した場合はtrue、それ以外はfalse。
     */
    private boolean insert(Test test) throws Exception { // privateに変更し、saveからのみ呼び出されるように
        boolean success = false;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(insertSql)) {

            stmt.setString(1, test.getStudent().getNo());
            stmt.setString(2, test.getClassNum());
            stmt.setString(3, test.getSubject().getCd());
            stmt.setString(4, test.getSchool().getCd());
            stmt.setInt(5, test.getNo());
            stmt.setInt(6, test.getPoint());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("成績の挿入中にデータベースエラーが発生しました。", e);
        }
        return success;
    }

    /**
     * 既存のテストレコードをデータベースで更新する。
     * @param test 更新するデータを含むTestオブジェクト。
     * @return 更新が成功した場合はtrue、それ以外はfalse。
     */
    private boolean update(Test test) throws Exception { // privateに変更し、saveからのみ呼び出されるように
        boolean success = false;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(updateSql)) {

            stmt.setInt(1, test.getPoint());
            stmt.setString(2, test.getStudent().getNo());
            stmt.setString(3, test.getClassNum());
            stmt.setString(4, test.getSubject().getCd());
            stmt.setString(5, test.getSchool().getCd());
            stmt.setInt(6, test.getNo());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("成績の更新中にデータベースエラーが発生しました。", e);
        }
        return success;
    }

    /**
     * Testデータを保存（存在すれば更新、なければ挿入）する。
     * @param test 保存するTestオブジェクト。
     * @return 保存が成功した場合はtrue、それ以外はfalse。
     */
    public boolean save(Test test) throws Exception {
        // まず既存の成績があるかチェック
        Test existingTest = get(test.getStudent().getNo(), test.getSubject().getCd(), test.getNo(), test.getSchool().getCd());

        if (existingTest == null) {
            // 存在しない場合は新規挿入
            return insert(test);
        } else {
            // 存在する場合は更新
            return update(test);
        }
    }
}