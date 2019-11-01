<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				document.getElemenyById("mapsIcon").style.display = "none";
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
			document.getElementById("nav").style.display = "none";
			document.getElementById("logo").style.display = "none";
		
		}
	</script>
	
	<!-- Google Maps JavaScript -->
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
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
  	  		document.getElementById('nav').style.display = "block";
  	  		document.getElementById('mapsIcon').style.display = "block";
  	  		document.getElementById('logo').style.display = "block";
    	});
      }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFdAKoTEE_FVWKj61hSAjyN6fmb66VanY&callback=initMap"
    async defer></script>

<meta charset="UTF-8">

<!-- title -->
<title>WeatherMeister</title>


<!-- linking CSS -->
<link rel="stylesheet" type="text/css" href="HomePage.css" />
</head>

<!-- body -->
<body>
	<!-- topbar -->
	<div class="topbar">
		<h1>
			<a href="index.jsp" style="text-decoration: none; color: white;">WeatherMeister</a>
		</h1>
		<div class="topbarlogin">
			<h2>
				<a href=<%= (session.getAttribute("username") != null) ? "SearchHistory" : "login.jsp" %> id="login-profile"> <%= (session.getAttribute("username") != null) ? "Profile" : "Login" %></a>
			</h2>
			<h2>
				<a href=<%= (session.getAttribute("username") != null) ? "Logout" : "register.jsp" %> id="logout-register"> <%= (session.getAttribute("username") != null) ? "Logout" : "Register" %></a>
			</h2>
		</div>
	</div>

	<!-- logo -->
	<div class="logo" id="logo"></div>

	<!-- background image -->
	<div class="background-img"></div>

	<!-- City search -->
	<div class="nav" id="nav">
		<div class="search-container">
			<form action="MyServlet" method="GET">
				<div class="showCitySearch" id="showCitySearch">
					<input type="text" placeholder="Search..." name="citySearch">
					<button id="submitButton" type="submit" name="citySearch"></button>
				</div>	
				<div class="showLocationSearch" id="showLocationSearch">
					<input type="text" id="LatitudeSearch" placeholder="Latitude"
						name="latSearch"> <input type="text" id="LongitudeSearch"
						placeholder="Longitude" name="longSearch">
					<button id="submitButton" type="submit" name="locationSearch"></button>
					<button type="button" id="mapsIcon" name="mapsIcon" onclick="maps()"></button>
				</div>

				<div class="radio-button-div">
					<input type="radio" name="option" id="City" value="City"
						checked="checked" onclick="myFunction()">City
					<input type="radio" name="option" id="Location" value="Location"
						onclick="myFunction()">Location (Lat./Long.)
				</div>
			</form>
		</div>
	</div>
	
	<!--  Google Maps Overlay -->
	<div id="containThatMap" style="display: none">
		<div id="map"></div>
	</div>
</body>
</html>