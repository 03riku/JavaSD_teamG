package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Bean.Teacher;

public class TeacherDao extends Dao {

	public Teacher findAll() throws Exception {
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM school ORDER BY id ASC";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Teacher c = new Teacher();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                c.setPassword(rs.getString("password"));
                c.setSchool(rs.getString("school"));
            }
        }
        return c;
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
    }}
