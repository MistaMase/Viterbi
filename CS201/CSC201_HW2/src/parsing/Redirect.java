package parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

import com.google.gson.Gson;

/**
 * Servlet implementation class Redirect
 */
@WebServlet("/Redirect")
public class Redirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Redirect() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
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
				Statement search = null;
				try {
					// Use the userID to add to search table
					search = conn.createStatement();
					
					
					search.executeUpdate("INSERT INTO Searches (userID, search) VALUES (" + Integer.parseInt(rs.getString("userID")) + ", '" + ((ArrayList<OpenWeatherCity>)session.getAttribute("cityList")).get(Integer.parseInt(request.getParameter("city"))).getName() + "');");
					
				} catch (SQLException sqle) {
					System.out.println("SQLE: " + sqle.getMessage());
				} finally {
					try {
						if(search != null) rs.close();
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
				
		RequestDispatcher rd = request.getRequestDispatcher("city.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
