<%@page import="java.util.Comparator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>	
<%@ page import="parsing.City"%>	
<%@ page import="parsing.NotEnoughException"%>
<%@ page import="parsing.FormatException"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import = "java.util.Collections"%>
<%@page import ="java.util.Comparator"%>
<%@page import ="java.util.List"%>
<%@page import = "parsing.OpenWeatherCity" %>

<!DOCTYPE html>
<html>

<!-- head -->
<head>

<script type="text/javascript">

// displays and hides both types of search bars
function myFunction() {
	
	// show City search bar
	if (document.getElementById("City").checked == true) {
		document.getElementById("showCitySearch").style.display = "block";
		document.getElementById("showLocationSearch").style.display = "none";
		document.getElementById("mapsIcon").style.display = "none";
	} 
	
	// show Location search bar
	else {
		document.getElementById("showCitySearch").style.display = "none";
		document.getElementById("showLocationSearch").style.display = "block";
		document.getElementById("mapsIcon").style.display = "block";
	}
}

function maps() {
	
	document.getElementById("searchResults").style.display = "none";
	document.getElementById("infoTable").style.display = "none";
	document.getElementById("containThatMap").style.display = "block";
}

</script>

<!-- Google Maps Embedded -->
<script>
      var map;
      function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 40, lng: -98},
          zoom: 4
        });
        
        
        google.maps.event.addListener(map, 'click', function(event) {
    		document.getElementById('LatitudeSearch').value = event.latLng.lat();
  	  		document.getElementById('LongitudeSearch').value = event.latLng.lng();
  	  		document.getElementById('containThatMap').style.display = "none";
  	  		document.getElementById('searchResults').style.display = "block";
	  		document.getElementById("infoTable").style.display = "block";
    	});
      }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFdAKoTEE_FVWKj61hSAjyN6fmb66VanY&callback=initMap"
    async defer></script>


<meta charset="UTF-8">

<!-- title -->
<title>WeatherMeister</title>

<!-- linking CSS -->
<link rel="stylesheet" type="text/css" href="all.css" />
</head>

<!-- body -->
<body>

		<!-- topbar -->
	<div class="topbar">
		<h1>
			<a href="index.jsp" style="text-decoration: none; color: white;">WeatherMeister</a>
		</h1>
	</div>

	<!-- background image -->
	<div class="background-img"></div>

	<!-- City search -->
	<div class="nav">
		<div class="search-container">
			<form action="MyServlet" method="GET">
				<div class="showCitySearch" id="showCitySearch">
					<input type="text" placeholder="Search..." name="citySearch">
					<button type="submit"></button>
				</div>
	
				<div class="showLocationSearch" id="showLocationSearch">
					<input type="text" id="LatitudeSearch" placeholder="Latitude"
						name="latSearch"> <input type="text" id="LongitudeSearch"
						placeholder="Longitude" name="longSearch">
					<button type="submit"></button>
				</div>
				<div class="radio-button-div">
					<input type="radio" name="option" id="City" value="City"
						checked="checked" onclick="myFunction()">City <input
						type="radio" name="option" id="Location" value="Location"
						onclick="myFunction()">Location (Lat./Long.)
				</div>
				<button type="button" id="mapsIcon" name="mapsIcon" onclick="maps()" style="display: none;"></button>
			</form>

		</div>
	</div>
	<%
		ArrayList<OpenWeatherCity> search = (ArrayList<OpenWeatherCity>)session.getAttribute("cityList");
		String errorMessage;
		String searchResultsValue;
		if(search.size() > 0)
			errorMessage = "";
		else
			errorMessage = "No Cities Were Found";
		if(search.size() > 1)
			searchResultsValue = "Search Results";
		else
			searchResultsValue = "";
	%>
	<h2 id="searchResults"><%= searchResultsValue %></h2>
	<table id="infoTable">
		<%
			if(search.size() > 0) {
		%>
		<tr>
			<th>City Name</th>
			<th>Temp. Low</th>
			<th>Temp. High</th>
		</tr>
		
		<% 
		
		}
		for (int i = 0; i < search.size(); i++) {
	
		%>
		<tr>
			
			<td><a href = "Redirect?city=<%= i %>" style="text-decoration: none; color: white;"><%= search.get(i).getName()+", "+search.get(i).getCountry() %></a></td>
			
			<td><%= search.get(i).getTemp_Min() %></td>
			<td><%= search.get(i).getTemp_Max() %></td>
		</tr>

		<% } %>
	</table>
	<div id="noCityError"><%= errorMessage %></div>
	<!--  Google Maps Overlay -->
	<div id="containThatMap" style="display: none">
		<div id="map"></div>
	</div>
</body>
</html>