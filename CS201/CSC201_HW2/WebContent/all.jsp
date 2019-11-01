<%@page import="java.util.Comparator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>	
<%@ page import="parsing.OpenWeatherCity"%>	
<%@ page import="parsing.NotEnoughException"%>
<%@ page import="parsing.FormatException"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import = "java.util.Collections"%>
<%@page import ="java.util.Comparator"%>
<%@page import ="java.util.List"%>

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

function maps(){
	document.getElementById("sortBy").style.display = "none";
	document.getElementById("noCityError").style.display = "none";
	document.getElementById("custom-select").style.display = "none";
	document.getElementById("infoTable").style.display = "none";
	document.getElementById("allCitiesLabel").style.display = "none";
	document.getElementById("containThatMap").style.display = "block";
}


function sort() {
	var table = document.getElementById("infoTable");
	
	// A to Z
	if (document.getElementById("selectID").value == "0") {
		var table, rows, switching, i, x, y, shouldSwitch;
		  switching = true;
		  while (switching) {
		    switching = false;
		    rows = table.rows;
		    for (i = 1; i < (rows.length - 1); i++) {
		      shouldSwitch = false;
		      x = rows[i].getElementsByTagName("TD")[0];
		      y = rows[i + 1].getElementsByTagName("TD")[0];
		      
		      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		}
	}
	
	// Z to A
	else if (document.getElementById("selectID").value == "1") {
		var table, rows, switching, i, x, y, shouldSwitch;
		  switching = true;
		  while (switching) {
		    switching = false;
		    rows = table.rows;
		    for (i = 1; i < (rows.length - 1); i++) {
		      shouldSwitch = false;
		      x = rows[i].getElementsByTagName("TD")[0];
		      y = rows[i + 1].getElementsByTagName("TD")[0];
		      
		      if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		}
	}
			
	// Temp. Low Ascending
	else if (document.getElementById("selectID").value == "2") {
		var table, rows, switching, i, x, y, shouldSwitch;
		  switching = true;
		  while (switching) {
		    switching = false;
		    rows = table.rows;
		    for (i = 1; i < (rows.length - 1); i++) {
		      shouldSwitch = false;
		      x = rows[i].getElementsByTagName("TD")[1];
		      y = rows[i + 1].getElementsByTagName("TD")[1];
		      
		      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		}	
	}
	
	// Temp. Low Descending
	else if (document.getElementById("selectID").value == "3") {
		var table, rows, switching, i, x, y, shouldSwitch;
		  switching = true;
		  while (switching) {
		    switching = false;
		    rows = table.rows;
		    for (i = 1; i < (rows.length - 1); i++) {
		      shouldSwitch = false;
		      x = rows[i].getElementsByTagName("TD")[1];
		      y = rows[i + 1].getElementsByTagName("TD")[1];
		      
		      if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		}
	}
	
	// Temp. High Ascending
	else if (document.getElementById("selectID").value == "4") {
		var table, rows, switching, i, x, y, shouldSwitch;
		  switching = true;
		  while (switching) {
		    switching = false;
		    rows = table.rows;
		    for (i = 1; i < (rows.length - 1); i++) {
		      shouldSwitch = false;
		      x = rows[i].getElementsByTagName("TD")[2];
		      y = rows[i + 1].getElementsByTagName("TD")[2];
		      
		      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		}
	}
	
	// Temp. High Descending
	else if (document.getElementById("selectID").value == "5") {
		var table, rows, switching, i, x, y, shouldSwitch;
		  switching = true;
		  while (switching) {
		    switching = false;
		    rows = table.rows;
		    for (i = 1; i < (rows.length - 1); i++) {
		      shouldSwitch = false;
		      x = rows[i].getElementsByTagName("TD")[2];
		      y = rows[i + 1].getElementsByTagName("TD")[2];
		      
		      if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		}
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
  	  		document.getElementById('nav').style.display = "block";
  	  		document.getElementById('mapsIcon').style.display = "block";
	  		document.getElementById("sortBy").style.display = "block";
	  		document.getElementById("custom-select").style.display = "block";
	  		document.getElementById("infoTable").style.display = "block";
	  		document.getElementById("allCitiesLabel").style.display = "block";
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
	<div class="nav" id="nav">
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
				<button type="button" id="mapsIcon" name="mapsIcon" style="display: none;" onclick="maps();"></button>
			</form>
		</div>
	</div>
	<%
		ArrayList<OpenWeatherCity> search = (ArrayList<OpenWeatherCity>)session.getAttribute("cityList");
		String noCityError;
		String allCitiesValue;
		if(search.size() > 0)
			noCityError = "";
		else 
			noCityError = "No Cities Were Found";
		if(search.size() > 1)
			allCitiesValue = "All Cities";
		else
			allCitiesValue = "";
	%>

	<h2 id="allCitiesLabel"><%= allCitiesValue %></h2>
	<div class = "sortBy" id="sortBy">Sort by:</div>
	<table id="infoTable">
		<%
			if(search.size() > 0){
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
			<td><a href= "Redirect?city=<%= i %>" style="text-decoration: none; color: white;"><%= search.get(i).getName()+", " + search.get(i).getCountry() %></a></td>
			<td><%= search.get(i).getTemp_Min() %></td>
			<td><%= search.get(i).getTemp_Max() %></td>
		</tr>
	
		<% } %>
	</table>
	<div id="noCityError" style="display: block"><%= noCityError %></div>
	<div id="custom-select">
		<select id = "selectID" onchange="sort();">
			<option value="0" >City Name A-Z</option>
			<option value="1" >City Name Z-A</option>
			<option value="2" >Temp. Low ASC</option>
			<option value="3" >Temp. Low DESC</option>
			<option value="4"  >Temp. High ASC</option>
			<option value="5" >Temp. High DESC</option>
		</select>
	</div>
	
	<!--  Google Maps Overlay -->
	<div id="containThatMap" style="display: none">
		<div id="map"></div>
	</div>
</body>
</html>