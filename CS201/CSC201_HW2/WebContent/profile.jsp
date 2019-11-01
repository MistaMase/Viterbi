<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
	</head>
	<!-- linking CSS -->
<link rel="stylesheet" type="text/css" href="profile.css" />
</head>

<!-- body -->
<body>
 
		<!-- topbar -->
	<div class="topbar">
		<h1>
			<a href="index.jsp" style="text-decoration: none; color: white;">WeatherMeister</a>
		</h1>
		<div class="topbarlogin">
			<h3>
				<a href=<%= (session.getAttribute("username") != null) ? "SearchHistory" : "login.jsp" %> id="login-profile"> <%= (session.getAttribute("username") != null) ? "Profile" : "Login" %></a>
			</h3>
			<h3>
				<a href=<%= (session.getAttribute("username") != null) ? "Logout" : "register.jsp" %> id="logout-register"> <%= (session.getAttribute("username") != null) ? "Logout" : "Register" %></a>
			</h3>
		</div>
	</div>

	<!-- background image -->
	<div class="background-img"></div>
	<div id="userText">
		<h2><%= session.getAttribute("username") + "'s Search History" %></h2>
	</div>

	<table id="infoTable">
	
		<% 
		ArrayList<String> searches = (ArrayList<String>)session.getAttribute("searchHistory");
		for (int i = 0; i < searches.size(); i++) {
	
		%>
		<tr>
			<td style = "text-decoration: none; color: white;"><%= searches.get(i) %></a></td>
		</tr>

		<% } %>
	</table>
</body>
</html>