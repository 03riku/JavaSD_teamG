package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Bean.School;
import Bean.Teacher;

public class TeacherDao extends Dao {

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
			String school_cd = rs2.getString("school_cd");
			String school_name = rs2.getString("school_name");

			school.setCd(school_cd);
			school.setName(school_name);
			Teacher.setSchool(school);
		}
		return Teacher;
	}
}