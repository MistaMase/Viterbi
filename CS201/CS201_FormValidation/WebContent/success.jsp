<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String fname = request.getParameter("fname");
	String lname = request.getParameter("lname");
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
	</body>
</html>