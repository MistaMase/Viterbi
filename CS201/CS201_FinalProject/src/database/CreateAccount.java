package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class CreateAccount
 */
@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String nextPage = "index.jsp";
		HttpSession session = request.getSession();		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			// Connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/UserProfile?user=root&password=root");
						
			//Check if username exists
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM Profile WHERE username='" + request.getParameter("username") + "'");
			if(rs.next()) {
				System.out.println("Username taken");
				request.setAttribute("validUsername", false);
				nextPage = "register.jsp";
			}
			else {
				request.setAttribute("validUsername", true);
				// Check if two passwords are the same
				if(!request.getParameter("password").equals(request.getParameter("confirmPassword")) || request.getParameter("password").equals("")) {
					request.setAttribute("validPassword", false);
					nextPage = "register.jsp";
				}
				else {
					System.out.println("Working password");
					request.setAttribute("validPassword", true);
					// Create new account
					statement = conn.createStatement();
					
					//Current each user starts with 1000 credits
					
					statement.executeUpdate("INSERT INTO Profile (Username, Password, Credits) " + "VALUES (\"" + request.getParameter("username") 
						+ "\", \"" + request.getParameter("password") + "\", 1000 )");
					session.setAttribute("username", request.getParameter("username"));
					
					//Update user's credits on inital login
					try {
						statement = conn.createStatement();
						rs = statement.executeQuery("SELECT Credits FROM Profile WHERE Username='" + request.getParameter("username") + "'");
						
						//Can actually error check on this if necessary
						rs.next();
						
						int credits = rs.getInt("Credits");
						
						//Won't get her if credits throws error
						session.setAttribute("credits", credits);
						
					} catch (SQLException sqle) {
						System.out.println("Failure getting credits from database: " + sqle.getMessage());
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("CNFE: " + cnfe.getMessage());
		} finally {
			try {
				if(statement != null) statement.close();
				if(conn != null) conn.close();
			} catch (SQLException sqle) {
				System.out.println("SQLE closing stuff: " + sqle.getMessage());
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
