<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	String errorMessage = "";
	if(request.getAttribute("validUsername") != null){
		boolean validUsername = (boolean)request.getAttribute("validUsername");
		if (validUsername == false) {
			errorMessage = "Username does not exist.";
		}
	}
	if(request.getAttribute("validPassword") != null){
		boolean validPassword = (boolean)request.getAttribute("validPassword");
		if (validPassword == false) {
			errorMessage = "Incorrect Password.";
		}
	}
%> 
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Trojan Casino</title>
		<link href="https://fonts.googleapis.com/css?family=Bungee+Inline" rel="stylesheet">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<style>
			body {
				background-image: url('images/spinning_roulette.gif');
				background-attachment: fixed;
				background-size: cover;
				background-repeat: no-repeat;
			}
			#logo {
				font-family: 'Bungee Inline', cursive;
			}
			.username {
				position: absolute;
				top: 35%;
				left: 38%;
				width: 26%;
				height: 5%;
				font-size: 110%;
				border-radius: 20px;
			}
			.password {
				position: absolute;
				top: 52%;
				left: 38%;
				width: 26%;
				height: 5%;
				font-size: 110%;
				border-radius: 20px;
			}
			.loginSubmit {
				position: absolute;
				top: 65%;
				left: 43%;
				width: 16%;
				height: 8%;
				border-radius: 20px;
				background-color: lightblue;
				border-color: lightblue;
				font-size: 150%;
				color: white;
				font-family: 'Bungee Inline', cursive;
			}
			.usernameText {
				position: absolute;
				color: white;
				left: 38%;
				top: 30%;
				font-family: 'Bungee Inline', cursive;
				font-size: 130%;
			}
			.passwordText {
				position: absolute;
				color: white;
				left: 38%;
				top: 47%;
				font-family: 'Bungee Inline', cursive;
				font-size: 130%;
			}
			#login {
				position: absolute;
				top: 6%;
				margin-top: 0%;
				left: 69%;
				color: white;
				text-decoration: none;
				font-family: 'Bungee Inline', cursive;
				font-size: 110%;
			}
			#register {
				position: absolute;
				top: 6%;
				margin-top: 0%;
				left: 82%;
				color: white;
				text-decoration: none;
				font-family: 'Bungee Inline', cursive;
				font-size: 150%;
			}
			#back {
				position: absolute;
				text-decoration: none; 
				font-family: 'Bungee Inline', cursive; 
				color: white;
				top: 6%;
				left: 3%;
				font-size: 150%;
			}
			#navbar {
				position: absolute;
				height: 8.5%;
				width: 100%;
			}
			#errorMessage {
				position: absolute;
				color: #FAFA37;
				top: 58%;
				left: 38%;
				font-size: 250%;
			}
			
		</style>
		
		<script>
			function flashtext(ele,col) {
			    var tmpColCheck = document.getElementById( ele ).style.color;
	
			      if (tmpColCheck === 'silver') {
			        document.getElementById( ele ).style.color = col;
			      } else {
			        document.getElementById( ele ).style.color = 'silver';
			      }
		    } 
		    
		    setInterval(function() {
		        flashtext('usernameText', '#FAFA37');
		    }, 150 );
		    
		    setInterval(function() {
		        flashtext('passwordText', '#FF9933');
		    }, 175);
		    
		    setInterval(function() {
		        flashtext('loginSubmit', '#C154C1');
		    }, 200 );
		</script>
		<script>
			var colors  = [ 'red', '#00ff00', 'blue', 'white', 'yellow', 'pink', '#6600ff'];
			var current = 0;
		
			setInterval(function()
			{
				// Update element's style with the new color				
				document.getElementById('logo').style.color = colors[current];
		
				// Go to the next color
		
				current = (current + 1) % colors.length;
		
			}, 100);
				    
		</script>
		
	</head>
	<body>
		<nav class="navbar navbar-expand-md navbar-dark bg-dark">
        <ul class="navbar-nav mr-auto">

			<li class="nav-item">
     		 	<a style="font-size: 22px;"id="logo" class="navbar-brand" href="RedirectToIndex"><span class="navbar-logo">Trojan Casino</span></a>
             </li>
            <li class="nav-item">
                <a class="nav-link" href="RedirectToRoulette">Roulette</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="RedirectToJackpot">Jackpot</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="RedirectToSlots">Slots</a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="login.jsp">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="register.jsp">Register</a>
            </li>
        </ul>
</nav>
		
		<div id="errorMessage"> 
		
		<%if( errorMessage != null) { %>
		
			<%=errorMessage %> 
		
		<% } %>
		
		</div>
		
		<h3 class="usernameText" id="usernameText">Username</h3>
		<h3 class="passwordText" id="passwordText">Password</h3>
		<form method="POST" id="registerForm" action="Authenticate">
			<input style="outline:none;" type="text" name="username" class="username" id="loginUsername"/> <br/> <br/>
			<input style="outline:none;" type="password" name="password" class="password" id="loginPassword"/> <br/>
			<input style="outline:none;" type="submit" name="loginSubmit" id="loginSubmit" class="loginSubmit" value="Login"/>
		</form>
	</body>
</html>

