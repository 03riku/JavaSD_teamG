package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Bean.School;

public class SchoolDao extends Dao {

    // SQL文
    private static final String FIND_ALL_SQL = "SELECT school_cd, school_name FROM school";
    private static final String FIND_BY_CD_SQL = "SELECT schhol_cd, school_name FROM school WHERE school_cd = ?";

    // すべての学校を取得
    public List<School> findAll() {
        List<School> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                School school = new School();
                school.setCd(rs.getString("cd"));
                school.setName(rs.getString("name"));
                list.add(school);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 学校コードで1件取得
    public School find(String cd) {
        School school = null;

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_CD_SQL)) {

            stmt.setString(1, cd);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                school = new School();
                school.setCd(rs.getString("cd"));
                school.setName(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return school;
    }
}
