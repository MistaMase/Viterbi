package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchHistory
 */
@WebServlet("/SearchHistory")
public class SearchHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchHistory() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Get all search results from the database
				
		String nextPage = "failure.html";
		HttpSession session = request.getSession();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// Connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Assignment3?user=root&password=root");
			
			// Get the unique userID from their username
			st = conn.createStatement();
			rs = st.executeQuery("SELECT userID FROM Users WHERE username='" + session.getAttribute("username") + "'");
						
			if(rs.next()) {
				Statement searches = null;
				ResultSet searchResults = null;
				try {
					// Use the userID to search Searches table
					searches = conn.createStatement();
					searchResults = st.executeQuery("SELECT search FROM Searches WHERE userID='" + rs.getString("userID") + "'");
					ArrayList<String> searchList = new ArrayList<String>();
					while(searchResults.next()) 
						searchList.add(searchResults.getString("search"));
					
					session.setAttribute("searchHistory", searchList);
					nextPage = "profile.jsp";
					
				} catch (SQLException sqle) {
					System.out.println("SQLE: " + sqle.getMessage());
				} finally {
					try {
						if(searchResults != null) rs.close();
						if(searches != null) st.close();
					} catch (SQLException sqle) {
						System.out.println("SQLE closing stuff: " + sqle.getMessage());
					}
				}	
			}
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
