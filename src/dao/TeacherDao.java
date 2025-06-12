package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            String sql2 = "SELECT * FROM school WHERE  ORDER BY id ASC";
            PreparedStatement st2 = con.prepareStatement(sql2);
            ResultSet rs2 = st2.executeQuery();
            st.setInt(1, );

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
//Teacher オブジェクトに関連する School 情報を設定するため
//	getメソッド
	public Teacher get(String id) throws Exception {
		Teacher Teacher = new Teacher();
//	sql作成
		try (Connection con = getConnection()) {
			String sql = "SELECT * FROM Teacher WHERE id = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
//	sql結果受け取り
			String teacher_id = rs.getString("id");
			String teacher_password = rs.getString("password");
			String teacher_name = rs.getString("name");
			String teacher_school_cd = rs.getString("school_cd");
//	beanにセット
			Teacher.setId(teacher_id);
			Teacher.setPassword(teacher_password);
			Teacher.setName(teacher_name);

			School school = new School();
			String sql2 = "SELECT * FROM SCHOOL WHERE SCHOOL_CD = ?";
			PreparedStatement st2 = con.prepareStatement(sql2);
			st2.setString(1, teacher_school_cd);
	        ResultSet rs2 = st2.executeQuery();
			Teacher.setSchool(teacher_school_cd);
			String school_cd = rs2.getString("school_cd");
			String school_name = rs2.getString("school_name");

		}
		return Teacher;
	}
}
