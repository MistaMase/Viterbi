<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%String username = (String)session.getAttribute("username");
	//username = "thickboi";	
		Integer creditcount = (Integer)session.getAttribute("credits");
		if (creditcount == null)
			creditcount = 0;
	%>
	<link href="https://fonts.googleapis.com/css?family=Bungee+Inline" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<link href="https://fonts.googleapis.com/css?family=Roboto+Mono|Space+Mono" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	
	
	<title>Trojan Casino</title>
	
	<style>
		#timer{
			font-family: 'Roboto Mono', monospace;
		}
		#wheel{
            -webkit-transition: -webkit-transform 8s ease-out; 
            height: 100%;
        } 
        #wheelholder{
        	width:500px;
        	height:500px;
        	
        }
        #timerdiv{
        	color:white;
        	margin: 10px;
        	text-align: center;
        }
        #spin {
            /* margin:50px; */
            /* margin-left:auto;
            margin-right:auto; */
           
            display: flex;
            align-items: center;
            justify-content:center;
            margin-top:50px;
            margin-right: 45px;
            
            
        }
        #arrow{
            position: absolute;
            height:75px;
            width: 100px;
            
        }
        #arrowdiv{
            height:75px;
            width: 100px;
        }
        #jeffdiv{
        	width: 50%;
        	height:50%;
        	position:absolute;
        	top:0;
        	left:0;
        }
        #jeff{
        	width: 30%;
        	position: absolute;
        	top:32%;
        	left:34%;
        }
        
        #inputs{
        	width: 400px;
        	height: 500px;
        	background-color: rgb(52,58,64);
        	margin-right: 40px;
        	
        }
        #main{
        	display: flex;
        	align-items: center;
        	align-content: center;
        	justify-content: center;
      
        }
        .choice{
        	color: white;
        	font-family: Arial;
        	text-align: center;
        	height: 10px;
        	padding: 5px !important;
        	margin: 0 !important;
        	white-space: !important;
        	
        	
        }
        
        .red{
        	background-color: red;
        }
        .black {
        	background-color: black;
        }
        
        .maintable{
        	margin: 0 !important;
        	padding: 0 !important;
        }
        
        .choice input{
        	margin-left: 5px;
        	width: 90px;
        	padding: 0px;
        	background: transparent;
        	border: 1px solid white;
        	color: white;
        	border-radius: 5px;
        }
        .place{
        	width: 100%;
        	margin-right:auto !important;
        	margin-left: auto !important;
        	margin-top: 5px;
        }
        #logo{
        	font-family: 'Bungee Inline', cursive;
        }
        
        #roulettetitle{
        	font-family: 'Bungee Inline', cursive;
        	text-align:center;
        	color:white;
        	
        }
        #winmessage{
        	color:yellow;
        	text-align:center;
        }
	</style>
	
	
	<!-- COPIED THIS IN SO REMOVE LATER -->
	<script>
		var current = 0
	 	var colors  = [ 'red', '#00ff00', 'blue', 'white', 'yellow', 'pink', '#6600ff'];
	 

		    setInterval(function()
		    {			
				document.getElementById('logo').style.color = colors[current];
				
		        current = (current + 1) % colors.length;
	
		    }, 100);

			var count = 0;
			function connectToServer() {
				socket = new WebSocket("ws://localhost:8080/CS201_FinalProject/websocket");
				
				
				socket.onopen = function(event){
					socket.send("Roulette_initSend");
				}
			
				socket.onmessage = function(event){
					var serverfeed = event.data;
					var arrayfeed = serverfeed.split("_");
					
					if(arrayfeed[1] == "ROLL"){
						var rollVal = parseInt(arrayfeed[2]);
						setandspin(rollVal);
					}
					else if (arrayfeed[0] == "ROULETTE" && arrayfeed[1] == "TIME"){
						var c = parseFloat(arrayfeed[2]);
						count = c*100;
						console.log("Updating Timer");
						console.log(count);
						
					}
					
					else if(arrayfeed[1] == "USERNAME"){
						if(arrayfeed[2] == "<%=username%>"){
							setTimeout(function(){
								
								document.getElementById("winmessage").innerHTML = "You win " + arrayfeed[4] + " credits!";
								document.getElementById("usercredits").innerHTML = parseInt(document.getElementById("usercredits").innerHTML) + parseInt(arrayfeed[4]);
							
							}, 9000);
						}
					}
					
					
					
				}
				
				socket.onclose = function(event){
				}
				
			}
			function sendMessage(){
				//socket.send("ROULETTE_placeBet_USERNAME_thicboi_NUMBER_37_BET_123");
				var containerDiv = document.getElementById("inputs");
				var innerDivs = containerDiv.getElementsByTagName("input");
				
				for(var i=0; i<innerDivs.length; i++)
				{
					var message = "";
					if(innerDivs[i].value != null && innerDivs[i].value != ""){
						message = "ROULETTE_placeBet_USERNAME_" + "<%=username%>" + "_NUMBER_"+innerDivs[i].id + "_BET_" + innerDivs[i].value;
						console.log(message);
						socket.send(message);
						if(parseInt(document.getElementById("usercredits").innerHTML) - parseInt(innerDivs[i].value) >= 0){
							document.getElementById("usercredits").innerHTML = parseInt(document.getElementById("usercredits").innerHTML) - parseInt(innerDivs[i].value);
						}
						
					}
				     
				}
				for(var i=0; i<innerDivs.length; i++)
				{
					innerDivs[i].value = "";
				}
				
				return false;
			}
			
			//var img = document.getElementById("wheel");
	        //img.addEventListener('click', onClick, false);
	        //var mults = [23,2,10,4,28,66,14,31,18,29,33,12,35,20,6,25,1717,16,5,22,3,17,30,27,1,9,2727,13,15,32,19,7,24,3434,11,34];
	        var mults = [22,2,10,4,26,35,14,29,18,27,31,12,33,20,6,24,0,16,5,21,3,17,28,25,1,9,34,13,15,30,19,7,23,36,11,32];
	        var prevdeg = 0;
	        var spinmultiplier = 2;
	        
	        function setandspin(value){
	        	spinmultiplier = mults[value - 1];
	        	spin();
	        }
	        
	        function spin(){
	        		        	
	        	var img = document.getElementById("wheel")
	            img.removeAttribute('style');
	        	
	            img.setAttribute('style', '-webkit-transform:unset;');
	            
	           	
	            //var deg = 500 + Math.round(Math.random() * 1000);
	            var deg = 9.73*spinmultiplier + 720;
	            //prevdeg <= deg
	            if(Math.abs(prevdeg - deg) < 720){
	            	if(deg >= 720){
	            		deg = deg - 720;
	            	}
	            	else{
	            		deg = deg + 720;
	            	}
	            	
	            }
	            prevdeg = deg;
	            //17: 0, 25: 1, 2: 2, 21: 3, 4:4, 19:5, 15:6 etc 26 9 15:29 13:35
	            var css = '-webkit-transform: rotate(' + deg + 'deg);';
	            
	            
	            img.setAttribute('style', css);
	            
	            
	        }
	        function reset(){
	            document.getElementById("wheel").removeAttribute('style');
	        }
			
	        
	        //Interval of 10
	        var counter = setInterval(timer, 10);
	        function timer(){
	        	if(count <= 0){
	        		count = 6000;
	        	}
	        	count--;
	        	var num = count/100;
	        	num = num.toFixed(2);
	        	document.getElementById("timer").innerHTML = num;
	        }
	        
			
		</script>
</head>

<!-- Remove the automatic websocket connection if necessary -->
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
            <li class="nav-item">
               <a class="nav-link disabled" >Credits:</a>
            </li>
            <li class="nav-item">
               <a class="nav-link disabled" id="usercredits"><%=creditcount%></a>
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

<div id = "main">

	<div id = "spin">
	
		<div id = "wheelholder">
			
	        <img src="images/roulette2.png" id="wheel">
	        <div id = "timerdiv">
	        	<h1 id = "timer">20</h1>
	        </div>
	        
	    </div>
	    <div id = "arrowdiv">
	        <img src="images/arrow.png" id="arrow" >
	    </div>
	</div>
	<div id = "inputs">
	<h2 id ="roulettetitle">Roulette</h2>
		<table class = "table table-bordered maintable">
			<tbody>
				
				<tr>
					<td class = "choice red" id = "1"> &nbsp; 1 <input type = "text" id = "1"> </td>
					<td class = "choice black" id = "2"> &nbsp; 2 <input type = "text" id = "2"></td>
					<td class = "choice red" id = "3"> &nbsp; 3 <input type = "text" id = "3"> </td>	
				</tr>
				<tr>
					<td class = "choice black" id = "4"> &nbsp; 4 <input type = "text" id = "4"> </td>
					<td class = "choice red" id = "5"> &nbsp; 5 <input type = "text" id = "5"> </td>
					<td class = "choice black" id = "6"> &nbsp; 6 <input type = "text" id = "6"></td>	
				</tr>
				<tr>
					<td class = "choice red" id = "7"> &nbsp; 7 <input type = "text" id = "7"></td>
					<td class = "choice black" id = "8"> &nbsp; 8 <input type = "text" id = "8"></td>
					<td class = "choice red" id = "9"> &nbsp; 9 <input type = "text" id = "9"></td>	
				</tr>
				<tr>
					<td class = "choice black" id = "10"> 10 <input type = "text" id = "10"></td>
					<td class = "choice black" id = "11"> 11 <input type = "text" id = "11"></td>
					<td class = "choice red" id = "12"> 12 <input type = "text" id = "12"></td>	
				</tr>
				<tr>
					<td class = "choice black" id = "13"> 13 <input type = "text" id = "13"></td>
					<td class = "choice red" id = "14"> 14 <input type = "text" id = "14"></td>
					<td class = "choice black" id = "15"> 15 <input type = "text" id = "15"></td>	
				</tr>
				<tr>
					<td class = "choice red" id = "16"> 16 <input type = "text" id = "16"></td>
					<td class = "choice black" id = "17"> 17 <input type = "text" id = "17"></td>
					<td class = "choice red" id = "18"> 18 <input type = "text" id = "18"></td>	
				</tr>
				<tr>
					<td class = "choice red" id = "19"> 19 <input type = "text" id = "19"></td>
					<td class = "choice black" id = "20"> 20 <input type = "text" id = "20"></td>
					<td class = "choice red" > 21 <input type = "text" id = "21"></td>	
				</tr>
				<tr>
					<td class = "choice black"> 22 <input type = "text" id = "22"></td>
					<td class = "choice red"> 23 <input type = "text" id = "23"></td>
					<td class = "choice black"> 24 <input type = "text" id = "24"></td>	
				</tr>
				<tr>
					<td class = "choice red"> 25 <input type = "text" id = "25"></td>
					<td class = "choice black"> 26 <input type = "text" id = "26"></td>
					<td class = "choice red"> 27 <input type = "text" id = "27"></td>	
				</tr>
				<tr>
					<td class = "choice black"> 28 <input type = "text" id = "28"></td>
					<td class = "choice black"> 29 <input type = "text" id = "29"></td>
					<td class = "choice red"> 30 <input type = "text" id = "30"></td>	
				</tr>
				<tr>
					<td class = "choice black"> 31 <input type = "text" id = "31"></td>
					<td class = "choice red"> 32 <input type = "text" id = "32"></td>
					<td class = "choice black"> 33 <input type = "text" id = "33"></td>	
				</tr>
				<tr>
					<td class = "choice red"> 34 <input type = "text" id = "34"></td>
					<td class = "choice black"> 35 <input type = "text" id = "35"></td>
					<td class = "choice red"> 36 <input type = "text" id = "36"></td>	
				</tr>
				
			</tbody>	
					
		</table>
		
		<table class = "table table-bordered maintable" > 
			<tbody>
				<tr>
					<td class = "choice"> EVEN <input type = "text" id = "37"> </td>
					<th  scope = "col" class = "choice"> &nbsp;&nbsp;&nbsp;&nbsp;ODD <input type = "text" id = "38"></th>	
				</tr>
				<tr>
					<td class = "choice red"> &nbsp; RED<input type = "text" id = "39"> </td>
					<th  scope = "col" class = "choice black"> BLACK <input type = "text" id = "40"></th>	
				</tr>
			</tbody>
		</table>
		<button type = "button" class="btn btn-success place" onclick = "sendMessage()">Place Bets</button>	
		<h1 id = "winmessage"></h1>
	</div>
	<!-- <button id="reset" onclick="reset()">Click to reset</button> -->
</div>



</body>
</html>
