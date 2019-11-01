package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Credits {

	
	//Return -1 means didn't work
	//Return any positive number means that's the user's remaining credits.
	public static synchronized int updateCredits(String username, int credits) {				
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// Connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/UserProfile?user=root&password=root");
			
			// Check if username works
			st = conn.createStatement();
			rs = st.executeQuery("SELECT Credits FROM Profile WHERE Username='" + username + "'");
			if(rs.next()) {
				
				int currentCredits = rs.getInt("Credits");
				int newCredits = currentCredits + credits;
				if(newCredits < 0)
					return -1;
				
				st = conn.createStatement();
				st.executeUpdate("UPDATE Profile SET credits = '" + newCredits  + "' WHERE username='" + username + "'"); 
				return newCredits;

				
			}
			
			// Invalid username
			else {
				System.out.println("Update Credits - Invalid Username");
				return -1;
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
		
		//Try-Catch failed
		return -1;	
	}
	
	public static synchronized int getUserCredits(String username) {
		if(username == null || username.equalsIgnoreCase("null"))
			return 0;
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// Connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/UserProfile?user=root&password=root");
			
			// Check if username works
			st = conn.createStatement();
			rs = st.executeQuery("SELECT Credits FROM Profile WHERE Username='" + username + "'");
			if(rs.next()) {
				int currentCredits = rs.getInt("Credits");
				return currentCredits;
			}
			
			// Invalid username
			else {
				System.out.println("Update Credits - Invalid Username");
				return 0;
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
		
		//Try-Catch failed
		return 0;	
	}
}
