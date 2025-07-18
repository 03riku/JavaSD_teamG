package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Student; // Student Beanをインポート
import Bean.Subject; // Subject Beanをインポート
import Bean.Test;

public class TestListSubjectDao extends Dao {

    private final String baseSql =
        // StudentとSubjectの情報を取得するためにJOINし、SELECT句も拡張
        "SELECT t.student_no, s.name AS student_name, s.ent_year, s.class_num AS student_class_num, s.is_attend, s.school_cd AS student_school_cd, " +
        "t.class_num AS test_class_num, t.subject_cd, sub.name AS subject_name, sub.school_cd AS subject_school_cd, " +
        "t.no, t.point, t.school_cd AS test_school_cd " +
        "FROM test AS t " +
        "JOIN student AS s ON t.student_no = s.no AND t.school_cd = s.school_cd " +
        "JOIN subject AS sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd " +
        // WHERE句に t.no を追加
        "WHERE s.ent_year = ? AND t.class_num = ? AND t.subject_cd = ? AND t.no = ? AND t.school_cd = ?";
        // 注意: `t.student` と `t.subject` はカラム名として適切ではありません。
        // `student_no` と `subject_cd` など、DBのカラム名に合わせるべきです。
        // (ご提示のコードでは既にstudent_no, subject_cdが使われているので問題ありません)

    private final String insertSql =
        "INSERT INTO test (student_no, class_num, subject_cd, school_cd, no, point) VALUES (?, ?, ?, ?, ?, ?)";

    private List<Test> postFilter(ResultSet rset) throws Exception {
        List<Test> list = new ArrayList<>();

        while (rset.next()) {
            Test test = new Test();

            // Student オブジェクトの生成とセット
            Student student = new Student();
            student.setNo(rset.getString("student_no"));        // 学生番号をセット
            student.setName(rset.getString("student_name"));    // 学生名をセット
            student.setEntYear(rset.getInt("ent_year"));        // 入学年度をセット
            student.setClassNum(rset.getString("student_class_num")); // studentテーブルのクラス番号をセット
            student.setAttend(rset.getBoolean("is_attend"));    // 在学フラグをセット

            // StudentにSchoolオブジェクトをセットするために、ResultSetから取得したschool_cdを使用
            School studentSchool = new School();
            studentSchool.setCd(rset.getString("student_school_cd")); // エイリアスで取得
            student.setSchool(studentSchool);                       // StudentにSchoolオブジェクトをセット
            test.setStudent(student);                               // TestにStudentオブジェクトをセット


            // Subject オブジェクトの生成とセット
            Subject subject = new Subject();
            subject.setCd(rset.getString("subject_cd"));        // 科目コードをセット
            subject.setName(rset.getString("subject_name"));    // 科目名をセット

            // SubjectにSchoolオブジェクトをセットするために、ResultSetから取得したschool_cdを使用
            School subjectSchool = new School();
            subjectSchool.setCd(rset.getString("subject_school_cd")); // エイリアスで取得
            subject.setSchool(subjectSchool);                       // SubjectにSchoolオブジェクトをセット
            test.setSubject(subject);                               // TestにSubjectオブジェクトをセット


            test.setClassNum(rset.getString("test_class_num")); // TESTテーブルのクラス番号 (エイリアスで取得)

            // Testオブジェクト自身のSchoolをセット
            School testSchool = new School();
            testSchool.setCd(rset.getString("test_school_cd")); // TESTテーブルの学校コード (エイリアスで取得)
            test.setSchool(testSchool);


            test.setNo(rset.getInt("no"));    // 試験回数
            test.setPoint(rset.getInt("point")); // 点数

            list.add(test);
        }

        return list;
    }

    // ★★ filterメソッドのシグネチャとPreparedStatementのバインドを修正します ★★
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) {
        List<Test> result = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(baseSql)) {

            stmt.setInt(1, entYear);
            stmt.setString(2, classNum);
            stmt.setString(3, subject.getCd());
            stmt.setInt(4, num); // ★★ ここで 'num' をバインドする ★★
            stmt.setString(5, school.getCd());

            ResultSet rset = stmt.executeQuery();
            result = postFilter(rset);

        } catch (Exception e) {
            e.printStackTrace();
            // エラー時に適切なメッセージをセットしたり、再スローを検討することも重要です
        }

        return result;
    }

    /**
     * Inserts a new test record into the database.
     * @param test The Test object containing the data to be inserted.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean insert(Test test) {
        boolean success = false;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(insertSql)) {

            stmt.setString(1, test.getStudent().getNo()); // Studentオブジェクトから学生番号を取得
            stmt.setString(2, test.getClassNum());
            stmt.setString(3, test.getSubject().getCd()); // Subjectオブジェクトから科目コードを取得
            stmt.setString(4, test.getSchool().getCd());  // Schoolオブジェクトから学校コードを取得
            stmt.setInt(5, test.getNo()); // 試験回数
            stmt.setInt(6, test.getPoint());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}