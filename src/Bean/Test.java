package Bean;

import javax.servlet.http.HttpServlet;

public class Test extends HttpServlet {
	private String student;
	private String classNum;
	private String subject;
	private School school;
	private int no;
	private int point;

	public String getStudent() {
		return student;
	}

	public String getClassNum() {
		return classNum;
	}

	public String getSubject() {
		return subject;
	}

	public School getSchool() {
		return school;
	}

	public int getNo() {
		return no;
	}

	public int getPoint(){
		return point;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void setPoint(int point){
		this.point = point;
	}

}
