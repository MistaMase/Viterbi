package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Authenticate
 */
@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authenticate() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession();
		
		String nextPage = "login.jsp";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// Connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Assignment3?user=root&password=root");
			
			// Check if username works
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Users WHERE username='" + request.getParameter("username") + "'");
			if(rs.next()) {
				request.setAttribute("validUsername", true);
				Statement ps = null;
				ResultSet checkPs = null;
				try {
					ps = conn.createStatement();
					checkPs = st.executeQuery("SELECT * FROM Users WHERE username='" + request.getParameter("username") + "' AND password='" + request.getParameter("password") + "'");
					
					if(checkPs.next()) {
						request.setAttribute("validPassword", true);
						session.setAttribute("username", request.getParameter("username"));
						nextPage = "index.jsp";
					}
					else 
						request.setAttribute("validPassword", false);
					
				} catch (SQLException sqle) {
					System.out.println("SQLE: " + sqle.getMessage());
				} finally {
					try {
						if(ps != null) rs.close();
						if(checkPs != null) st.close();
					} catch (SQLException sqle) {
						System.out.println("SQLE closing stuff: " + sqle.getMessage());
					}
				}
			}
			else
				request.setAttribute("validUsername", false);
			
	
			RequestDispatcher rd = request.getRequestDispatcher(nextPage);
			rd.forward(request, response);
			
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("CNFE: " + cnfe.getMessage());
		} finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				if(conn != null) conn.close();
			} catch (SQLException sqle) {
				System.out.println("SQLE closing stuff: " + sqle.getMessage());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
