<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  
   %>
   
   
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%
	
	String username = (String)session.getAttribute("username");
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
            flex-direction: column;
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
        	
        	justify-content: center;
        	color: white;
        	background-color: rgb(52,58,64);
        	width: 50%;
        	height: 300px;
        	margin-right: auto;
        	margin-left:auto;
        	margin-top: 50px;
        	border-radius: 10px;
        	text-align: center;
        	flex-direction: column;
        	overflow: auto;
      
        }
        #main ul{
        	margin-top: 10px;
        	font-size: 100%;
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
        #leaderlist{
        	display: flex;
        	align-items: center;
        	justify-content:center
        }
	</style>
	
	
	<!-- COPIED THIS IN SO REMOVE LATER -->
	<script>
		
	</script>
</head>

<!-- Remove the automatic websocket connection if necessary -->
<body background="images/background.jpg" onload="connectToServer()">

<nav class="navbar navbar-expand-md navbar-dark bg-dark">
        <ul class="navbar-nav mr-auto">

			<li class="nav-item">
     		 	<a style="font-size: 22px;"id="logo" class="navbar-brand" href="index.jsp"><span class="navbar-logo">Trojan Casino</span></a>
             </li>
            <li class="nav-item">
                <a class="nav-link" href="roulette.jsp">Roulette</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="jackpot.jsp">Jackpot</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="slots.jsp">Slots</a>
            </li>
        </ul>
        <%if(username == null) {%>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="login.jsp">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="register">Register</a>
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
	<div>
		<h1>LEADERBOARD</h1>
	</div>
	<div id = "leaderlist">
		<ol>
			<li>
				bicrollboi
			</li>
			
		</ol>
	</div>
	
</div>



</body>
</html>
