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

public class TestDao extends Dao { // BaseDaoからDaoに変更されている点に注意

    // Test BeanにResultSetのデータをセットするヘルパーメソッド
    private Test setTestFromResultSet(ResultSet rSet, School school) throws SQLException {
        Test test = new Test();
        Student student = new Student();
        Subject subject = new Subject();

        // 学生情報
        student.setNo(rSet.getString("student_no"));
        student.setName(rSet.getString("student_name")); // JOINで取得
        student.setEntYear(rSet.getInt("ent_year"));     // JOINで取得
        student.setClassNum(rSet.getString("class_num")); // JOINで取得
        student.setAttend(rSet.getBoolean("is_attend")); // JOINで取得

        // ここを修正します
        String studentSchoolCd = rSet.getString("school_cd");
        School studentSchool = new School();
        studentSchool.setCd(studentSchoolCd);
        // 必要であれば、ここでSchoolDaoを使って学校名などを取得し、studentSchoolにセットするロジックを追加
        // 例: SchoolDao schoolDao = new SchoolDao(); School fullSchool = schoolDao.get(studentSchoolCd); student.setSchool(fullSchool);
        student.setSchool(studentSchool); // 生成したSchoolオブジェクトをセット


        // 科目情報
        subject.setCd(rSet.getString("subject_cd"));
        subject.setName(rSet.getString("subject_name")); // JOINで取得

        // ここも修正します
        String subjectSchoolCd = rSet.getString("school_cd"); // subjectの学校コード
        School subjectSchool = new School();
        subjectSchool.setCd(subjectSchoolCd);
        // 必要であれば、ここでSchoolDaoを使って学校名などを取得し、subjectSchoolにセットするロジックを追加
        subject.setSchool(subjectSchool); // 生成したSchoolオブジェクトをセット


        test.setStudent(student);
        test.setSubject(subject);
        test.setNo(rSet.getInt("no")); // 試験回数
        test.setPoint(rSet.getInt("point"));
        test.setClassNum(rSet.getString("class_num_test")); // TESTテーブルのclass_num
        test.setSchool(school); // Test Bean自身のSchoolオブジェクトは引数で受け取ったものをセット

        return test;
    }


    /**
     * 指定された条件でテスト結果を検索します。
     * @param entYear   入学年度 (null の場合、条件に含めない)
     * @param classNum  クラス番号 (null または空の場合、条件に含めない)
     * @param subjectCd 科目コード (null または空の場合、条件に含めない)
     * @param schoolCd  学校コード (必須)
     * @return 検索されたTestオブジェクトのリスト
     * @throws Exception SQLエラーまたはデータ取得エラー
     */
    public List<Test> filter(Integer entYear, String classNum, String subjectCd, String schoolCd) throws Exception {
        List<Test> tests = new ArrayList<>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rSet = null;

        try {
            con = getConnection(); // DaoからConnectionを取得 (BaseDaoからDaoに名前が変わった場合)

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    T.STUDENT_NO, S.NAME AS student_name, S.ENT_YEAR, S.CLASS_NUM, S.IS_ATTEND, S.SCHOOL_CD, "); // S.SCHOOL_CD を追加
            sql.append("    T.SUBJECT_CD, SUB.NAME AS subject_name, SUB.SCHOOL_CD AS subject_school_cd, "); // SUB.SCHOOL_CD を追加しエイリアスを付ける
            sql.append("    T.NO, T.POINT, T.CLASS_NUM AS class_num_test ");
            sql.append("FROM TEST AS T ");
            sql.append("JOIN STUDENT AS S ON T.STUDENT_NO = S.NO AND T.SCHOOL_CD = S.SCHOOL_CD ");
            sql.append("JOIN SUBJECT AS SUB ON T.SUBJECT_CD = SUB.CD AND T.SCHOOL_CD = SUB.SCHOOL_CD ");
            sql.append("WHERE T.SCHOOL_CD = ? ");

            List<Object> paramList = new ArrayList<>();
            paramList.add(schoolCd);

            if (entYear != null) {
                sql.append("AND S.ENT_YEAR = ? ");
                paramList.add(entYear);
            }
            if (classNum != null && !classNum.isEmpty()) {
                sql.append("AND T.CLASS_NUM = ? ");
                paramList.add(classNum);
            }
            if (subjectCd != null && !subjectCd.isEmpty()) {
                sql.append("AND T.SUBJECT_CD = ? ");
                paramList.add(subjectCd);
            }

            sql.append("ORDER BY S.NO, SUB.CD, T.NO");

            pStmt = con.prepareStatement(sql.toString());

            for (int i = 0; i < paramList.size(); i++) {
                pStmt.setObject(i + 1, paramList.get(i));
            }

            rSet = pStmt.executeQuery();

            // School Bean (Test BeanでSchoolを持つ場合)
            // このschoolはTestオブジェクトのschoolフィールドにセットされる。
            // StudentやSubjectにセットするSchoolとは異なる場合があるので注意。
            // 必要であれば、ここでSchoolDaoを使って学校名なども取得し、より完全なSchoolオブジェクトを生成する
            School schoolObjectForTestBean = new School();
            schoolObjectForTestBean.setCd(schoolCd);
            // 例: schoolObjectForTestBean.setName(new SchoolDao().get(schoolCd).getName());

            while (rSet.next()) {
                tests.add(setTestFromResultSet(rSet, schoolObjectForTestBean));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースからのテスト結果取得中にエラーが発生しました。", e);
        } finally {
            try {
                if (rSet != null) rSet.close();
                if (pStmt != null) pStmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tests;
    }
}