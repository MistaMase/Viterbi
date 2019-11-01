<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%String username = (String)session.getAttribute("username");
Integer creditcount = (Integer)session.getAttribute("credits");
if (creditcount == null)
	creditcount = 0;
%>
<link href="https://fonts.googleapis.com/css?family=Bungee+Inline" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
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
		
<style>
				#logo {
					font-family: 'Bungee Inline', cursive;
				}
				
				body, html {
  width: 100%;
  margin: 0;
}

.container {
  width: 100%;
}

.leftpane {
    width: 25%;
    float: left;
    border-collapse: collapse;
}

.middlepane {
    width: 50%;
    float: left;
    border-collapse: collapse;
}

.rightpane {
  width: 25%;
  position: relative;
  float: right;
  border-collapse: collapse;
}

hr {
  margin-top: 1rem;
  margin-bottom: 1rem;
  border: 0;
  width: 90%;
  border-top: 2px solid rgba(255, 255, 255, 0.1);
}

.rainbow-button {
  width:calc(60vw + 10px);
  height:calc(10vw + 10px);
  background-image: linear-gradient(90deg, #00C0FF 0%, #FFCF00 49%, #FC4F4F 80%, #00C0FF 100%);
  border-radius:5px;
  display:flex;
  align-items:center;
  justify-content:center;
  text-transform:uppercase;
  font-size:3vw;
  font-weight:bold;
  animation:slidebg 3s linear infinite;
}
.rainbow-button:after {
  content:attr(alt);
  width:60vw;
  height:10vw;
  background-color:#191919;
  display:flex;
  align-items:center;
  justify-content:center;
}

@keyframes slidebg {
  to {
    background-position:20vw;
  }
}

</style>
<title>Trojan Casino</title>
</head>
<body background="images/background.jpg">

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
        <%if(username == null) {%>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="login.jsp">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="register.jsp">Register</a>
            </li>
        </ul>
        <%} else {%>
         <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="Logout">Logout</a>
            </li>
            	<li class="nav-item">
               <a class="nav-link disabled">Credits:</a>
            </li>
            <li class="nav-item">
               <a class="nav-link disabled" id="usercredits"><%=creditcount%></a>
            </li>
        </ul>
        <%} %>
</nav>

<br/>
<br/>
<br/>	
<br/>
<br/>
<div class="container">

  <a href="RedirectToRoulette">
	   <div class="leftpane">
	   <h2 style="text-align: center; color:white; font-size: 50px;">Roulette</h2>
	   <p style="color:#d9d9d9"> Play a game where a wheel determines your fate. Place some combination of bets on red, black, a number, or a specific lucky number.
	   </div>
  </a>
  <a href="RedirectToJackpot">
	  <div class="middlepane">
	  <h2 style="text-align: center; color:white; font-size: 50px;">Jackpot</h2>
	  <p style="color:#d9d9d9"> Test your luck in a game against other players. Jackpot is a lottery style gamemode where the amount wagered is proportional to your odds of winning all other users credits. Winners are selected every 30 seconds, and updates performed in real time. Bets are disabled 1 second before drawing.</p>
	  </div>
  </a>
  <a href="RedirectToSlots">
	  <div class="rightpane">
	      <h2 style="text-align: center; color:white; font-size: 50px;">Slots</h2>
	      <p style="color:#d9d9d9"> Pull a lever and see if you have what it takes to multiply your money. This single player game mode allows you to win up to 5 times your wager.</p>
	  </div>
  </a>
</div>

<div style="width: 100%;">
	<a href="RandomPage" class="rainbow-button" style="text-decoration: none; color:white; margin: 0 auto;"alt="Play A Random Game"></a>
</div>

<div style="width: 100%; text-align: center; padding-top: 30px">
	<p style="color: white">Developed by: JJ, Grant, Gabe, and Nathan</p>
</div>

</body>
</html>
