<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String fnameError = (String)request.getAttribute("fnameError");
	String lnameError = (String)request.getAttribute("lnameError");
	if(fnameError == null || fnameError.trim().length() == 0){
		fnameError = "";
	}
	if(lnameError == null || lnameError.trim().length() == 0){
		lnameError = "";
	}
	String fname = request.getParameter("fname");
	String lname = request.getParameter("lname");
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>My Form</title>
	</head>
	<body>
		<h2>My Name Form</h2>
		<form action="FormValidate" method="POST">
			First Name <input type="text" name = "fname" value="<%= fname %>" /><font color="red"><%= fnameError %></font><br />
			Last Name <input type="text" name="lname" value="<%= lname %>"/><font color="red"><%= lnameError %></font><br />
			<input type="submit" name="submit" value="Validate Me" />
		</form>
	</body>
</html>