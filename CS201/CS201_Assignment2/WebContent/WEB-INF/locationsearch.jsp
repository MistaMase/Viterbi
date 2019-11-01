<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
		<link href="styling.css" rel="stylesheet" type="text/css">
		
	</head>
	<body>
		<div class="titlebar">
			<div class="weathermeistertext">WeatherMeister</div>
		</div>
		<div class="bgimage"></div>
		<div class="homepage">
			<img class="weatherlogo" src="Images/logo.png">
		
			<div class="searchtypetext" id="searchtypetext">		
				<div class="citysearchtype">
					<input type="text" class="citysearch" value="Location" onfocus="this.value=''">
				</div>
				<div class="latlongsearchtype" id="latlongsearchtype">
					<input type="text" class="latsearch" value="Latitude" onfocus="this.value=''">
					<div class="latlongtextspacing"></div>
					<input type="text" class="longsearch" value="Longitude" onfocus="this.value=''">
				</div>
				<div class="searchiconspacing">
					<img class="searchicon" src="Images/magnifying_glass.jpeg">
				</div>
			</div>
			
			<div class="searchtypebutton">
				<label onclick="locationSearch()" for="citybtn"><input type="radio" name="searchtype" id="citybtn" class="citybutton" checked="checked" /><font color="white">City</font></label>
				<label onclick="latlongSearch()" for="latlongbtn"><input type="radio" name="searchtype" class="latlongbutton" id="latlongbtn" onclick="latlongSearch()" /><font color="white">Location (Lat./Long.)</font></label>
			</div>	
			
			<script type="text/javascript">	
			function locationSearch(){
				latlongsearch = document.getElementById("latlongsearchtype");
				latlongsearch.style.visibility = "hidden";
				citysearch = document.getElementById("citysearchtype");
				citysearch.style.display = "block";
			}
			
			function latlongSearch(){
				citysearch = document.getElementById("citysearchtype");
				citysearch.style.display = "none";
				latlongsearch = document.getElementById("latlongsearchtype");
				latlongsearch.style.display = "block";
			}
			locationSearch();
			</script>
				
			<button class="displayall"><font>Display All</font></button>
		</div>
		<div class="displayallpage">
			<div>
				<label>All Cities</label>
				<table>
					<tr>
						<th>City Name</th>
						<th>Temp. Low</th>
						<th>Temp. High</th>
					</tr>
					<tr>
						<!-- Add the dynamic table data here as td -->  
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</div>
				<label>Sort By</label>
				<select>
					<option value="citynameaz">City Name A-Z</option>
					<option value="citynameza">City Name Z-A</option>
					<option value="templowasc">Temp. Low ASC</option>
					<option value="templowdesc">Temp. Low DESC</option>
					<option value="temphighasc">Temp. High ASC</option>
					<option value="temphighdesc">Temp. High DESC</option>
				</select>
		</div>
		
	</body>
</html>