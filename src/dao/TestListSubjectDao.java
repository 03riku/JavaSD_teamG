package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Bean.School;
import Bean.Subject;
import Bean.Test;

public class TestListSubjectDao extends Dao {

    private final String baseSql =
        "SELECT t.student, t.class_num, t.subject, t.school_code, t.no, t.point " +
        "FROM test t " +
        "JOIN student s ON t.student = s.student_id " +
        "WHERE s.ent_year = ? AND t.class_num = ? AND t.subject = ? AND t.school_code = ?";

    private List<Test> postFilter(ResultSet rset) throws Exception {
        List<Test> list = new ArrayList<>();

        while (rset.next()) {
            Test test = new Test();
            test.setStudent(rset.getString("student"));
            test.setClassNum(rset.getString("class_num"));
            test.setSubject(rset.getString("subject"));

            School school = new School();
            school.setCd(rset.getString("school_code")); // Schoolクラスにgetter/setterが必要
            test.setSchool(school);

            test.setNo(rset.getInt("no"));
            test.setPoint(rset.getInt("point"));

            list.add(test);
        }

        return list;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, School school) {
        List<Test> result = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(baseSql)) {

            stmt.setInt(1, entYear);
            stmt.setString(2, classNum);
            stmt.setString(3, subject.getCd()); // Subjectクラスにgetterが必要
            stmt.setString(4, school.getCd());   // Schoolクラスにgetterが必要

            ResultSet rset = stmt.executeQuery();
            result = postFilter(rset);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
