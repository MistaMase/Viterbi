<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String fnameError = (String)request.getAttribute("fnameError");
	String lnameError = (String)request.getAttribute("lnameError");
	String genderError = (String)request.getAttribute("genderError");
	String housingratingError = (String)request.getAttribute("housingratingError");
	String betterSchoolError = (String)request.getAttribute("betterSchoolError");
	if(fnameError == null || fnameError.trim().length() == 0){
		fnameError = "";
	}
	if(lnameError == null || lnameError.trim().length() == 0){
		lnameError = "";
	}
	if(genderError == null || genderError.trim().length() == 0)
		genderError = "";
	if(housingratingError == null || housingratingError.trim().length() == 0)
		housingratingError = "";
	if(betterSchoolError == null || betterSchoolError.trim().length() == 0)
		betterSchoolError = "";
	String fname = request.getParameter("fname") == null ? "" : request.getParameter("fname");
	String lname = request.getParameter("lname") == null ? "" : request.getParameter("lname");
	String gender = request.getParameter("gender") == null ? "" : request.getParameter("gender");
	String housingrating = request.getParameter("housingrating") == null ? "5" : request.getParameter("housingrating");
	String month = request.getParameter("month") == null ? "" : request.getParameter("month");
	String betterSchool = request.getParameter("betterSchool") == null ? "USC" : request.getParameter("betterSchool");
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Lab 4</title>
	</head>
	<body>
		<h2>Lab 4: Let's Get This Info</h2>
		<form action="FormValidate" method="POST">
			First Name <input type="text" name = "fname" value="<%= fname %>" /><font color="red"><%= fnameError %></font><br />
			Last Name <input type="text" name="lname" value="<%= lname %>"/><font color="red"><%= lnameError %></font><br />
			<input type="radio" name="gender" value="Male"  <%= gender.trim().equals("male") ? "checked" : "" %> />Male
			<input type="radio" name="gender" value="Female" <%= gender.trim().equals("female") ? "checked" : "" %> />Female
			<font color="red"><%= genderError %></font><br />
			How was your USC Housing experience?<br />
			<input type="range" name="housingrating" min="0" max="10" step="1" value="<%= housingrating %>" /><br />
			<font color="red"><%= housingratingError %></font><br />
			Here's a quote from a student.<br />
			<textarea cols="50">"USC is a good school."</textarea><br />
			What month were you born?
			<input type="month" name="month"/><br />
			Which school is better?
			<select name="betterSchool">
				<option value="USC">USC</option>
				<option value="UCLA">UCLA</option>
			</select>
			<font color="red"><%= betterSchoolError %></font><br />
			<input type="reset" value="Reset">
			<input type="submit" name="submit" value="Validate Me" />
		</form>
	</body>
</html>