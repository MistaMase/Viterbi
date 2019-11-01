<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="parsing.City"%>
<%@ page import="parsing.NotEnoughException"%>
<%@ page import="parsing.FormatException"%>
<%@ page import="parsing.OpenWeatherCity"%>
<%@ page import="java.util.ArrayList"%>

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
	document.getElementById("containThatMap").style.display = "block";
	document.getElementById("location-img").style.display = "none";
	document.getElementById("location-dtls").style.display = "none";
	document.getElementById("tempLow-img").style.display = "none";
	document.getElementById("tempLow-dtls").style.display = "none";
	document.getElementById("tempHigh-img").style.display = "none";
	document.getElementById("tempHigh-dtls").style.display = "none";
	document.getElementById("wind-img").style.display = "none";
	document.getElementById("wind-dtls").style.display = "none";
	document.getElementById("humidity-img").style.display = "none";
	document.getElementById("humidity-dtls").style.display = "none";
	document.getElementById("coords-img").style.display = "none";
	document.getElementById("coords-dtls").style.display = "none";
	document.getElementById("currTemp-img").style.display = "none";
	document.getElementById("currTemp-dtls").style.display = "none";
	document.getElementById("sun-img").style.display = "none";
	document.getElementById("sun-dtls").style.display = "none";
	document.getElementById("location-dtls").style.display = "none";
	document.getElementById("location-img").style.display = "none";
	document.getElementById("currTemp-img").style.display = "none";
	document.getElementById("currTemp-dtls").style.display = "none";
	document.getElementById("sun-dtls").style.display = "none";
	document.getElementById("sun-img").style.display = "none";
	document.getElementById("location").style.display = "none";
	document.getElementById("tempLow").style.display = "none";
	document.getElementById("tempHigh").style.display = "none";
	document.getElementById("wind").style.display = "none";
	document.getElementById("humidity").style.display = "none";
	document.getElementById("coords").style.display = "none";
	document.getElementById("currTemp").style.display = "none";
	document.getElementById("sun").style.display = "none";
	document.getElementById("cityName").style.display = "none";



	
}

// displays and hides icons and details
function iconFunction(x) {
	if (x.getAttribute('id') == "location-img") {
		document.getElementById("location-img").style.display = "none";
		document.getElementById("location-dtls").style.display = "block";
	} if (x.getAttribute('id') == "tempLow-img") {
		document.getElementById("tempLow-img").style.display = "none";
		document.getElementById("tempLow-dtls").style.display = "block";
	} if (x.getAttribute('id') == "tempHigh-img") {
		document.getElementById("tempHigh-img").style.display = "none";
		document.getElementById("tempHigh-dtls").style.display = "block";
	} if (x.getAttribute('id') == "wind-img") {
		document.getElementById("wind-img").style.display = "none";
		document.getElementById("wind-dtls").style.display = "block";
	} if (x.getAttribute('id') == "humidity-img") {
		document.getElementById("humidity-img").style.display = "none";
		document.getElementById("humidity-dtls").style.display = "block";
	} if (x.getAttribute('id') == "coords-img") {
		document.getElementById("coords-img").style.display = "none";
		document.getElementById("coords-dtls").style.display = "block";
	} if (x.getAttribute('id') == "currTemp-img") {
		document.getElementById("currTemp-img").style.display = "none";
		document.getElementById("currTemp-dtls").style.display = "block";
	} if (x.getAttribute('id') == "sun-img") {
		document.getElementById("sun-img").style.display = "none";
		document.getElementById("sun-dtls").style.display = "block";
	} if (x.getAttribute('id') == "location-dtls") {
		document.getElementById("location-dtls").style.display = "none";
		document.getElementById("location-img").style.display = "block";
	} if (x.getAttribute('id') == "tempLow-dtls") {
		document.getElementById("tempLow-dtls").style.display = "none";
		document.getElementById("tempLow-img").style.display = "block";
	} if (x.getAttribute('id') == "tempHigh-dtls") {
		document.getElementById("tempHigh-dtls").style.display = "none";
		document.getElementById("tempHigh-img").style.display = "block";
	} if (x.getAttribute('id') == "wind-dtls") {
		document.getElementById("wind-dtls").style.display = "none";
		document.getElementById("wind-img").style.display = "block";
	} if (x.getAttribute('id') == "humidity-dtls") {
		document.getElementById("humidity-dtls").style.display = "none";
		document.getElementById("humidity-img").style.display = "block";
	} if (x.getAttribute('id') == "coords-dtls") {
		document.getElementById("coords-dtls").style.display = "none";
		document.getElementById("coords-img").style.display = "block";
	} if (x.getAttribute('id') == "currTemp-dtls") {
		document.getElementById("currTemp-dtls").style.display = "none";
		document.getElementById("currTemp-img").style.display = "block";
	} if (x.getAttribute('id') == "sun-dtls") {
		document.getElementById("sun-dtls").style.display = "none";
		document.getElementById("sun-img").style.display = "block";
	} 
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
  	  	document.getElementById("location-img").style.display = "block";
  		document.getElementById("tempLow-img").style.display = "block";
  		document.getElementById("tempHigh-img").style.display = "block";
  		document.getElementById("wind-img").style.display = "block";
  		document.getElementById("humidity-img").style.display = "block";
  		document.getElementById("coords-img").style.display = "block";
  		document.getElementById("currTemp-img").style.display = "block";
  		document.getElementById("sun-img").style.display = "block";
  		document.getElementById("location-img").style.display = "block";
  		document.getElementById("currTemp-img").style.display = "block";
  		document.getElementById("sun-img").style.display = "block";
  		document.getElementById("location").style.display = "block";
  		document.getElementById("tempLow").style.display = "block";
  		document.getElementById("tempHigh").style.display = "block";
  		document.getElementById("wind").style.display = "block";
  		document.getElementById("humidity").style.display = "block";
  		document.getElementById("coords").style.display = "block";
  		document.getElementById("currTemp").style.display = "block";
  		document.getElementById("sun").style.display = "block";
  		document.getElementById("cityName").style.display = "block";
    	});
      }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFdAKoTEE_FVWKj61hSAjyN6fmb66VanY&callback=initMap"
    async defer></script>

<meta charset="UTF-8">

<!-- title -->
<title>WeatherMeister</title>

<!-- linking CSS -->
<link rel="stylesheet" type="text/css" href="city.css" />
</head>

<!-- body -->
<body>
	
		<!-- topbar -->
	<div class="topbar">
		<h1>
			<a href="index.jsp" style="text-decoration: none; color: white;">WeatherMeister</a>
		</h1>
		</div>
	</div>
	
	<!-- background image -->
	<div class="background-img"></div>

	<!-- City search -->
	<div class="nav">
		<div class="search-container">
			<form action = "MyServlet" method = "GET">
				<div class="showCitySearch" id="showCitySearch">
					<input type="text" placeholder="Search..." name="citySearch">
					<button type="submit"></button>
				</div>
				
				<div class="showLocationSearch" id="showLocationSearch">
					<input type="text" id = "LatitudeSearch" placeholder="Latitude" name="latSearch">
					<input type="text" id = "LongitudeSearch" placeholder="Longitude" name="longSearch">
					<button type="submit"></button>
				</div>
				<div class="radio-button-div">
					<input type="radio" name="option" id="City" value="City" checked="checked" onclick="myFunction()">City
					<input type="radio" name="option" id="Location" value="Location" onclick="myFunction()">Location (Lat./Long.)
				</div>
				<button type="button" id="mapsIcon" name="mapsIcon" style="display: none;" onclick="maps()"></button>
			</form>

		</div>
	</div>

	<% 
		OpenWeatherCity search = ((ArrayList<OpenWeatherCity>)session.getAttribute("cityList")).get(Integer.parseInt(request.getParameter("city"))); 
	%>

	<h2 id="cityName"><%= search.getName() %></h2>
	<div id = "location-img" onclick = "iconFunction(this)"></div><div class = "location" id="location">Location</div>
	<div id = "location-dtls" onclick = "iconFunction(this)"><%= search.getName() %>,<br><%= search.getCountry() %></div>
	<div id = "tempLow-img" onclick = "iconFunction(this)"></div><div class = "tempLow" id="tempLow">Temp Low</div>
	<div id = "tempLow-dtls" onclick = "iconFunction(this)"><%= search.getTemp_Min() %><br>degrees Fahrenheit</div>
	<div id = "tempHigh-img" onclick = "iconFunction(this)"></div><div class = "tempHigh" id="tempHigh">Temp High</div>
	<div id = "tempHigh-dtls" onclick = "iconFunction(this)"><%= search.getTemp_Max()  %><br>degrees Fahrenheit</div>
	<div id = "wind-img" onclick = "iconFunction(this)"></div><div class = "wind" id="wind">Wind</div>
	<div id = "wind-dtls" onclick = "iconFunction(this)"><%= search.getWindSpeed() %><br>miles/hour</div>
	<div id = "humidity-img" onclick = "iconFunction(this)"></div><div class = "humidity" id="humidity">Humidity</div>
	<div id = "humidity-dtls" onclick = "iconFunction(this)"><%= search.getHumidity() %>%</div>
	<div id = "coords-img" onclick = "iconFunction(this)"></div><div class = "coords" id="coords">Coordinates</div>
	<div id = "coords-dtls" onclick = "iconFunction(this)"><%= search.getLat() %>,<br><%= search.getLon() %></div>
	<div id = "currTemp-img" onclick = "iconFunction(this)"></div><div class = "currTemp" id="currTemp">Current Temp</div>
	<div id = "currTemp-dtls" onclick = "iconFunction(this)"><%= search.getTemp() %><br>degrees Fahrenheit</div>
	<div id = "sun-img" onclick = "iconFunction(this)"></div><div class = "sun" id="sun">Sunrise/set</div>
	<div id = "sun-dtls" onclick = "iconFunction(this)"><%= search.getSunrise() %>,<br><%= search.getSunset() %></div>
	
	<!--  Google Maps Overlay -->
	<div id="containThatMap" style="display: none">
		<div id="map"></div>
	</div>
</body>
</html>