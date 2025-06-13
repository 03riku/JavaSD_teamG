package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Subject;
import Bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    private String baseSql = "SELECT * FROM test_list_subject "
            + "WHERE ent_year = ? AND class_num = ? AND subject_id = ? AND school_id = ?";

    // データベースから取得したResultSetをTestListSubjectのリストに変換
    private List<TestListSubject> postFilter(ResultSet rset) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        while (rset.next()) {
            TestListSubject subject = new TestListSubject();
            subject.setId(rset.getInt("id"));
            subject.setEntYear(rset.getInt("ent_year"));
            subject.setClassNum(rset.getString("class_num"));
            subject.setSubjectId(rset.getInt("subject_id"));
            subject.setSchoolId(rset.getInt("school_id"));
            subject.setName(rset.getString("name")); // 例: nameカラムが存在する場合
            list.add(subject);
        }

        return list;
    }

    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) {
        List<TestListSubject> result = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(baseSql)) {

            stmt.setInt(1, entYear);
            stmt.setString(2, classNum);
            stmt.setInt(3, subject.getId());
            stmt.setInt(4, school.getId());

            ResultSet rset = stmt.executeQuery();
            result = postFilter(rset);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
