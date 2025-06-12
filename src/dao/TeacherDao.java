package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Bean.School;
import Bean.Teacher;

public class TeacherDao extends Dao {

	public Teacher findAll() throws Exception {
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM Teacher ORDER BY id ASC";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            Teacher c = new Teacher();
            if (rs.next()) {

            	rs.getString("id");
            	rs.getString("name");
            	rs.getString("password");

                c.setId("id");
                c.setName("name");
                c.setPassword("password");
            }

//			schoolの検索
            String sql2 = "SELECT * FROM school WHERE ORDER BY id ASC";
            PreparedStatement st2 = con.prepareStatement(sql2);
            ResultSet rs2 = st2.executeQuery();

            School school = new School();
            if (rs.next()) {

            	rs.getString("SCHOOL_CD");
            	rs.getString("SCHOOL_NAME");

            	school.setCd("cd");
            	school.setName("name");

            	c.setSchool(school);
            }
            return c;
        }

    }

    public Teacher findById(int id) throws Exception {
        Teacher c = null;

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM school WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                c = new Teacher();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
            }
        }

        return c;
    }
}
