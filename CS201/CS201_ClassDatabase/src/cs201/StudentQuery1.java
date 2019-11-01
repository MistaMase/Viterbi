package cs201;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentQuery1")
public class StudentQuery1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public StudentQuery1() {
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("fname");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentgrades?user=root&password=jackson123");
			ps = conn.prepareStatement("SELECT * FROM Student WHERE fname=?");
			ps.setString(1, firstName);
			rs = ps.executeQuery();
			while(rs.next()) {
				int studentID = rs.getInt("studentID");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				System.out.println(studentID + " - " + fname + " " + lname);
			}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("CNFE: " + cnfe.getMessage());
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException sqle) {
				System.out.println("SQLE closing stuff: " + sqle.getMessage());
			}
		}
	}

}
