package Bean;

import javax.servlet.http.HttpServlet;

public class ClassNum extends HttpServlet {
	private String num;
	private String school;

	public String getNum() {
		return num;
	}

	public String getSchool() {
		return school;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setSchool(String school) {
		this.school = school;
	}
}
