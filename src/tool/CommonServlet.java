package tool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CoomonServlet extends HttpServlet 全サーブレット共通の処理を表す抽象クラス
 *
 */
public abstract class CommonServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			get(req, resp);
		} catch (Exception e) {
			// 開発用エラー表示
			PrintWriter out = resp.getWriter();
			e.printStackTrace(out);

			// 本番用エラー表示
			// e.printStackTrace();
			// resp.sendRedirect("/shop/error");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			post(req, resp);
		} catch (Exception e) {
			// 開発用エラー表示
			PrintWriter out = resp.getWriter();
			e.printStackTrace(out);

			// 本番用エラー表示
			// e.printStackTrace();
			// resp.sendRedirect("/shop/error");
		}
	}

	/**
	 * ページごとのGETの処理を記述
	 *
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	protected abstract void get(HttpServletRequest req, HttpServletResponse resp) throws Exception;

	/**
	 * ページごとのPOSTの処理を記述
	 *
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	protected abstract void post(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
