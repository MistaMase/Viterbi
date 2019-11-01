package cs201;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FormValidate")
public class FormValidate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FormValidate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String nextPage = "/success.jsp";
		String gender = request.getParameter("gender");
		String housingrating = request.getParameter("housingrating");
		String betterSchool = request.getParameter("betterSchool");
		if(fname == null || fname.trim().length() == 0) {
			request.setAttribute("fnameError", "First Name needs a value.");
			nextPage = "/form.jsp";
		}
		if(lname == null || lname.trim().length() == 0) {
			request.setAttribute("lnameError", "Last Name needs a value.");
			nextPage = "/form.jsp";
		}
		if(gender == null || !(gender.trim().equals("Male") || gender.trim().equals("Female"))) {
			request.setAttribute("genderError", "A gender must be specified.");
			nextPage = "/form.jsp";
		}
		if(housingrating == null || Integer.valueOf(housingrating) < 0 || Integer.valueOf(housingrating) > 10) {
			request.setAttribute("housingratingError", "A rating for USC housing must be specified");
			nextPage = "/form.jsp";
		}
		if(betterSchool == null || !betterSchool.trim().equals("USC")) {
			request.setAttribute("betterSchoolError", "That's the wrong choice");
			nextPage = "/form.jsp";
		}
		//Pass the name of the page you want to redirect to
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
