package jhe.lin.boo.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class User
 */
@WebServlet("/User/Insert")
public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Insert() {
		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/alpha";
		String user = "bqq";
		String password = "123456";
		Connection conn = null;
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, password);
			doInsert(
					request.getParameter("id"), 
					request.getParameter("name"), 
					request.getParameter("age"), 
					request.getParameter("role"), 
					request.getParameter("sex"), 
					conn);
//			if (!conn.isClosed())
//				System.out.println("資料庫連線成功");

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void doInsert(String id, String name, String age, String role, String sex, Connection conn)
			throws SQLException {
		try {
			conn.setAutoCommit(false);
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO alpha.USER ");
			sb.append("(`id`,`name`,`age`,`role`,`sex`)");
			sb.append("VALUES(?,?,?,?,?);");
			PreparedStatement insertPs = conn.prepareStatement(sb.toString());
			insertPs.setString(1, id);
			insertPs.setString(2, name);
			insertPs.setString(3, age);
			insertPs.setString(4, role);
			insertPs.setString(5, sex);
			insertPs.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}
	}

}
