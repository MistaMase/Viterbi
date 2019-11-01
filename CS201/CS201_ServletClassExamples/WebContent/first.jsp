<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>

<%
	String fname = request.getParameter("fname");
	for(int i = 0; i < 10; i++){
%>	
	<%= i %> Hello <%= fname %>!<br />
<%
	}
%>

	</body>
</html>