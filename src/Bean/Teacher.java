package Bean;

import javax.servlet.http.HttpServlet;

public class Teacher extends HttpServlet {
	private String id;
	private String name;
	private String password;
	private School school;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public School getSchool() {
		return school;
	}

	public void setCd(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}
