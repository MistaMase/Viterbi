<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String fname = request.getParameter("fname");
	String lname = request.getParameter("lname");
	String gender = request.getParameter("gender");
	String housingrating = request.getParameter("housingrating");
	String month = request.getParameter("month");
	String betterSchool = request.getParameter("betterSchool");
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Successful Form Submission</title>
	</head>
	<body>
		<h2>Success Form</h2>
		First Name = <%= fname %><br />
		Last Name = <%= lname %><br />
		Gender = <%= gender %><br />
		Housing Rating = <%= housingrating %><br />
		Birthday = <%= month %><br />
		School Choice = <%= betterSchool %><br />
	</body>
</html>