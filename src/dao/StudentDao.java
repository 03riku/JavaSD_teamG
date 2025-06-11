package dao;

import java.sql.ResultSet;
import java.util.List;

import Bean.School;
import Bean.Student;

public class StudentDao extends Dao {

	private String basesql;

	public Student get(String no) throws Exception {

	}

	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {

	}

	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception{

	}

	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {

	}

	public List<Student> filter(School school, boolean isAttend) throws Exception {

	}

}
