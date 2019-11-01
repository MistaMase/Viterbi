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
		<div class="templocation">
			<!--  City Name Here -->
			<label></label>
			<div id="icongroup">
				<img class="icon" id="earth" src="Images/planet-earth.png">
				<img class="icon" id="snowflake" src="Images/snowflake.png">
				<img class="icon" id="sun" src="Images/sun.png">
				<img class="icon" id="wind" src="Images/wind.png">
				<img class="icon" id="drop" src="Images/drop.png">
				<img class="icon" id="location" src="Images/LocationIcon.png">
				<img class="icon" id="thermometer" src="Images/thermometer.png">
				<img class="icon" id="sunrise" src="Images/sunrise-icon.png">
			</div>
			<div id="icongroup">
				<div class="iconinfo" id="earthinfo">
					<label><font color="red">Earth</font></label>
				</div>
				<div class="iconinfo" id="snowflakeinfo">
					<label><font color="red">Earth</font></label>
				</div>
				<div class="iconinfo" id="suninfo">
					<label><font color="red">Earth</font></label>
				</div>
				<div class="iconinfo" id="windinfo">
					<label><font color="red">Earth</font></label>
				</div>
				<div class="iconinfo" id="dropinfo">
					<label><font color="red">Earth</font></label>
				</div>
				<div class="iconinfo" id="locationinfo">
					<label><font color="red">Earth</font></label>
				</div>
				<div class="iconinfo" id="thermometerinfo">
					<label><font color="red">Earth</font></label>
				</div>
				<div class="iconinfo" id="sunriseinfo">
					<label><font color="red">Earth</font></label>
				</div>
			</div>
		</div>
	</body>
</html>