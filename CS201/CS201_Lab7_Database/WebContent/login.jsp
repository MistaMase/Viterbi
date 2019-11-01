<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<%@ page import="cs201.DatabaseStuff" %>
	</head>
	<body>
		<form action="Authenticate" method="POST">
			Username: <input type="text" name="username" /><br />
			Password: <input type="text" name="password" /><br />
			<input type="submit" name="submit">
		</form>
	</body>
</html>