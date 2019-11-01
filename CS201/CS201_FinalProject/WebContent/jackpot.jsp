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

<link href="https://fonts.googleapis.com/css?family=Roboto+Mono|Space+Mono" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Bungee+Inline" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="https://cdn.jsdelivr.net/npm/canvas-confetti@0.2.0/dist/confetti.browser.min.js"></script>
<script type="text/javascript">

      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      
      var stringOfBets = "";
      
      function drawChart() {
    	  
     	    var options = {
       	          pieHole: 0.4,
       	          legend: {position: 'none'},
       	          backgroundColor: { fill:'transparent' }
       	    };
     	   
     	    var data = new google.visualization.DataTable();
     	  	
     	  	data.addColumn('string', 'Username');
     	  	data.addColumn('number', 'Bet Amount');
     	  	var userArray = stringOfBets.split("_");
     	  		for(i = 0, j = 1; i < userArray.length; i+=2, j+=2){
    	     	  	data.addRows([
    		       	  	  [userArray[i], parseInt(userArray[j])]
    		       	]);
     	  		}
     	  	
        	var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
        	chart.draw(data, options);

      }
    </script>
	<script type="text/javascript">
		var count=60.00; //original timer
		var socket;
		var betQuantity;
		
		function sendBet(){
			betQuantity = document.getElementById('betbar').value;
			sendMessage();
		}
		function connectToServer() {
			socket = new WebSocket("ws://localhost:8080/CS201_FinalProject/websocket");
			
			socket.onopen = function(event){
				socket.send("Jackpot_initSend");
			}
		
			socket.onmessage = function(event){
				var serverfeed = event.data;
				var arrayfeed = serverfeed.split("_");
				if(arrayfeed != null){
					if(arrayfeed[0] == "JACKPOT" && arrayfeed[1] == "WINNER"){
						if(document.getElementById('theusername').innerHTML == arrayfeed[2]){
							var originalNumber = parseInt(document.getElementById('usercredits').innerHTML);
				    		document.getElementById('usercredits').innerHTML = originalNumber + parseInt(arrayfeed[3]);
							confetti({
								  spread: 180,
								  particleCount: 200,
								  origin: {
									  y: 0.8
								  }
								});
						}
						printWinner(arrayfeed[2], arrayfeed[3]);
						toBottom();

					}
					else if(arrayfeed[0] == "JACKPOT" && arrayfeed[1] == "BET"){
						printBet(arrayfeed[2], arrayfeed[3]);
				    	document.getElementById('creditcounter').innerHTML = arrayfeed[4] + " Credits in the Pot";
				    	toBottom();
				    	var tempArrayMiller = stringOfBets.split("_");
				    	var repeatBet = false;
				    	
				    	
				    	if(tempArrayMiller.length > 1){
					    	for(var j = 0; j < tempArrayMiller.length; j+=2){
					    		if(tempArrayMiller[j] == arrayfeed[2]){
					    			repeatBet = true;
					    			var intInArray= parseInt(tempArrayMiller[j+1]);
					    			var inInRead = parseInt(arrayfeed[3]);
					    			tempArrayMiller[j+1] = (intInArray+inInRead).toString();
					    			stringOfBets = ""; //null out string
					    			for(var i = 0; i < tempArrayMiller.length-1; i+=2){ //reparse the shit
								    	stringOfBets += tempArrayMiller[i] + "_" + tempArrayMiller[i+1] + "_";
					    			}
					    			break;
					    		}
					    	}
				    	}
				    	
				    	if(document.getElementById('theusername').innerHTML == arrayfeed[2]){
				    		var originalNumber = parseInt(document.getElementById('usercredits').innerHTML);
				    		document.getElementById('usercredits').innerHTML = originalNumber - parseInt(arrayfeed[3]);
						}
				    	
						
				    	if(!repeatBet){
				    		stringOfBets += arrayfeed[2] + "_" + arrayfeed[3] + "_";
				    	}
				    	
				    	
				    	drawChart();
				    	
					}
					else if(arrayfeed[0] == "JACKPOT" && arrayfeed[1] == "RESET"){
						printReset();
				    	document.getElementById('creditcounter').innerHTML = "0 Credits in the Pot";
						toBottom();
						stringOfBets = "";
						drawChart();
						
					}
					else if(arrayfeed[0] == "JACKPOT" && arrayfeed[1] == "TIME" ){
				    	document.getElementById('creditcounter').innerHTML = arrayfeed[3] + " Credits in the Pot";
						count = arrayfeed[2];
						document.getElementById("time").style.visibility = "visible";
						toBottom();
					}
				}
			}
			
			socket.onclose = function(event){
			}
			
		}
		function sendMessage(){
			socket.send("JACKPOT_placeBet_USERNAME_" + document.getElementById('theusername').innerHTML + "_BET_" + String(betQuantity));
			return false;
		}
		
	</script>
    <script>
    
    var counter=setInterval(timer, 10); //1000 will  run it every 1 second

    function timer()
    {
      count=count-0.01;
      if (count < 0)
      {
    	  count=60.00;
      }

     document.getElementById("time").innerHTML = Math.round(count * 100) / 100 ;
    }
    </script>
    <script>
    function isValidBet(){ 
    	if(isNaN(document.getElementById('betbar').value) || document.getElementById('betbar').value < 1){
    		document.getElementById('livefeed').innerHTML += "<p style='color:red'>'" + document.getElementById('betbar').value + "' is not a valid bet.</p>";
    		toBottom();
    	}
    	return false;
    }
    function printWinner(winnername, credits){ 
    	document.getElementById('livefeed').innerHTML += "<p style='color:green'> The lucky player was " + winnername + " who won " + credits + " credits!</p>";
    	return false;
    }
    function printBet(user, credits){ 
    	document.getElementById('livefeed').innerHTML += "<p>" + user + " just placed a bet of " + credits + " credits.</p>";
    	return false;
    }
    function printReset(){ 
    	document.getElementById('livefeed').innerHTML += "<p>Game is reset, now taking bets for next game.</p>";
    	return false;
    }
    </script>
    <script>
    window.onload = toBottom;
    function toBottom() {
      var objDiv = document.getElementById('livefeed'); //Obtain the ID of the chat div
      objDiv.scrollTop = objDiv.scrollHeight; //Set the scroll offset position to the height of the chat div
    }
    </script>
    <style>
    
	p {
    	margin: 0;
    	padding: 0;
    	font-size:14px
	}
	
	#usercredits p {
		margin: 0;
    	padding: 0;
    	font-size:14px;
    	display: inline;
	}
	</style>
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
</style>
    
<title>Trojan Casino</title>
</head>
<body background="images/background.jpg" onload="connectToServer()">

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
<div style="display: flex; align-items: flex-start; justify-content: center;width: 90%;margin: 0 auto;">
	<div>
		<h2 style="color:white; font-size: 40px; font-family: 'Bungee Inline';">Jackpot</h2> 
		<h2 id="creditcounter" style="color:#E1E1E1; font-size: 20px">0 Credits in the Pot</h2>
		<div class="donutchart" id="donutchart" style="width: 600px; height:600px;"></div>
	</div>

	<div>
	<div>
	<p id="time" style="margin-top: 170px;color: white; font-size: 40px; font-family: 'Roboto Mono'; visibility:hidden;">60.00</p>
	</div>
	  <div id="livefeed" class="card card-body bg-dark" style="color: white; position:absolute; overflow: scroll; height: 300px; width: 287px;">
	  	<b style="font-size: 30 px;">Console:</b>
	  </div>
	  <br/>
	  <div class="input-group" style="margin-top: 288px">
	      <input id ="betbar" type="text" class="form-control" placeholder="">
	      <span class="input-group-btn">
	        <button class="btn btn-default btn-dark" type="button" onclick="sendBet(); isValidBet();">Place Bet</button>
	      </span>
	  </div>
	</div>
</div>

<div id="theusername" style="visibility: hidden;"><%=username%></div>

</body>
</html>
