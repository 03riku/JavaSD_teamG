package Bean;

import javax.servlet.http.HttpServlet;

public class User extends HttpServlet {
	private boolean isAuthenticated;

	public boolean getIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

}
