<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>

	<%String username = (String)session.getAttribute("username");
	//username = "thickboi";	
		Integer creditcount = (Integer)session.getAttribute("credits");
		if (creditcount == null)
			creditcount = 0;
	%>
	<meta charset="UTF-8">
	
	<link href="https://fonts.googleapis.com/css?family=Bungee+Inline" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script>


		    var colorss  = [ 'red', '#00ff00', 'blue', 'white', 'yellow', 'pink', '#6600ff'];
		    var currents = 0;

		    setInterval(function()
		    {
		        // Update element's style with the new color				
document.getElementById('logo').style.color = colorss[currents];

		        // Go to the next color

		        currents = (currents + 1) % colorss.length;

		    }, 100);
		    
</script>
	<style>

				
		body {
		    margin: 0;
		    padding: 0;
		  }
		
		.slotimg{
		    width: 100%;
		    height: 100%;
		
		}
		  
		  #stage {
		    margin: 0 auto;
		    margin-top:10px;
		   
		    width: 400px;
		    padding: 0 0 40px;
		    padding-top:10px;
		    padding-bottom: -100px;
		    background-color: rgb(52, 58, 64);
		    border-radius: 50px;
		    height: 500px;
		    
		  }
		  
		  .perspective-on {
		    -webkit-perspective: 1000px;
		       -moz-perspective: 1000px;
		            perspective: 1000px;    /* Setting the perspective of the contents of the stage but not the stage itself*/
		  }
		  .perspective-off {
		    -webkit-perspective: 0;
		       -moz-perspective: 0;
		            perspective: 0;
		  }
		  
		  #rotate {
		    margin: 0 auto 0;
		    width: 270px;
		    height: 220px;
		    padding-top:150px;
		    padding-bottom:200px;
		    margin-left: auto;
		    margin-right:auto;
		    /* Ensure that we're in 3D space */
		    /* display: flex;
		    justify-content: center;
		    align-content: center; */
		   
		
		    transform-style: preserve-3d;
		  }
		  
		  .ring {
		    margin: 0 auto;
		    height: 80px;
		    width: 90px;
		    float: left; 
		    
		    transform-style: preserve-3d;
		    
		  
		  }
		  .slot {
		    position: absolute;
		    overflow: hidden;
		    width: 90px;
		    height: 80px;
		    box-sizing: border-box;
		    opacity: 0.9;
		    color: rgba(0,0,0,0.9);
		    background: #fff;
		    border: solid 2px #000;
		    -webkit-backface-visibility: hidden;
		       -moz-backface-visibility: hidden;
		            backface-visibility: hidden;
		  }
		  
		  .backface-on {
		    -webkit-backface-visibility: visible;
		       -moz-backface-visibility: visible;
		            backface-visibility: visible;
		  }
		  
		  .slot p {
		    font-size: 36px;
		    font-weight: bold;
		    line-height: 80px;
		    margin: 0;
		    text-align: center;
		  }
		  
		  .go {
		    display: block;
		    margin: 5px auto 20px;
		    padding:15px 30px;
		    font-size: 20px;
		    cursor: pointer;
		    border-radius: 35px;
		    background-color: #4150f4;
		    color: white;
		    
		   
		  }
		  label {
		    cursor: pointer;
		    display: inline-block;
		    width: 45%;
		    text-align: center;
		  }
		  .tilted {
		    transform: rotateY(45deg);
		  }
		  /*=====*/
		  .spin-0     { transform: rotateX(-3719deg); }
		  .spin-1     { transform: rotateX(-3749deg); }
		  .spin-2     { transform: rotateX(-3779deg); }
		  .spin-3     { transform: rotateX(-3809deg); }
		  .spin-4     { transform: rotateX(-3839deg); }
		  .spin-5     { transform: rotateX(-3869deg); }
		  .spin-6     { transform: rotateX(-3899deg); }
		  .spin-7     { transform: rotateX(-3929deg); }
		  .spin-8     { transform: rotateX(-3959deg); }
		  .spin-9     { transform: rotateX(-3989deg); }
		  .spin-10    { transform: rotateX(-4019deg); }
		  .spin-11    { transform: rotateX(-4049deg); }
		  /*=====*/
		  @keyframes back-spin {
		      /*0%    { transform: rotateX(0deg); }*/
		      100%  { transform: rotateX(30deg); }
		  }
		  @keyframes tiltin {
		      0%    { transform: rotateY(0deg);}
		      50%   { transform: rotateY(0deg);}
		      100%  { transform: rotateY(45deg);}
		  }
		  @keyframes tiltout {
		      0%    { transform: rotateY(45deg);}
		      100%  { transform: rotateY(0deg);}
		  }
		  
		  @keyframes spin-0 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3719deg); }
		  }
		  @keyframes spin-1 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3749deg); }
		  }
		  @keyframes spin-2 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3779deg); }
		  }
		  @keyframes spin-3 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3809deg); }
		  }
		  @keyframes spin-4 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3839deg); }
		  }
		  @keyframes spin-5 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3869deg); }
		  }
		  @keyframes spin-6 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3899deg); }
		  }
		  @keyframes spin-7 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3929deg); }
		  }
		  @keyframes spin-8 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3959deg); }
		  }
		  @keyframes spin-9 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-3989deg); }
		  }
		  @keyframes spin-10 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-4019deg); }
		  }
		  @keyframes spin-11 {
		      0%    { transform: rotateX(30deg); }
		      100%  { transform: rotateX(-4049deg); }
		  }
		  
		  #buttons{
		  	position:relative;
		  	margin: auto;
		  }
		  #stage{
		  	
		  }
		  .game{
		  	margin-top:50px;
		  }
		  #animation{
		  	position:relative;
		  	margin-left:auto;
		  	margin-right:auto;
		  	display:flex;
		  	justify-content:center;
		  	
		  	
		  }
		  
		  #slottitle{
		  	font-family: 'Bungee Inline', cursive;
		  	color: yellow;
		  }
		  
		  #creditmessage{
		  	text-align: center;
		  	color: yellow;
		  }
		  
		  
		  #logo {
					font-family: 'Bungee Inline', cursive;
				}
		  
				
	</style>
	
	<title>Trojan Casino</title>
	
	
	<!-- COPIED THIS IN SO REMOVE LATER -->
	<script>
			/////////////////////
			
			//////////////////////
			//alert(onMessage);
			
			function connectToServer() {
				socket = new WebSocket("ws://localhost:8080/CS201_FinalProject/websocket");

				socket.onopen = function(event){
					socket.send("Slots_initSend");
				}
			
				socket.onmessage = function(event){
					var serverfeed = event.data;
					var arrayfeed = serverfeed.split("_");
					console.log(arrayfeed);
					
					//SLOTS_USERNAME_" + username + "_PAYOUT_" + makeString(numbers)
					//SLOTS_USERNAME_username_PAYOUT_4_1_6_60
					if(arrayfeed[0] == "SLOTS" && arrayfeed[3] == "PAYOUT"){
						var args = [parseInt(arrayfeed[4]),parseInt(arrayfeed[5]), parseInt(arrayfeed[6])];
						console.log("Logging args");
						console.log(args);
						customSpin(args);
						setTimeout(function(){
							document.getElementById("creditmessage").innerHTML = "You win " + arrayfeed[7] + " credits!";
							console.log("Array feed: " + arrayfeed);
							document.getElementById("usercredits").innerHTML = arrayfeed[8];
							
						}, 
					4000);
					}
					
					
					
					
				}	
				socket.onclose = function(event){
				}
				
			}
			
			function playGame(){
				socket.send("SLOTS_placeBet_USERNAME_" + "<%= username %>");
			}
			
			let result = "1,2,3";
			
			const SLOTS_PER_REEL = 12; //12
			// radius = Math.round( ( panelWidth / 2) / Math.tan( Math.PI / SLOTS_PER_REEL ) ); 
			// current settings give a value of 149, rounded to 150
			const REEL_RADIUS =150; //150

			function createSlots (ring) {
				
				var slotAngle = 360 / SLOTS_PER_REEL;

			    var seed = getSeed();
			    //var images = ["berry.jpg", "strawberry.png", "melon.png", "orange.png", "yosh.png", "miller.jpg"];

			    var images = ["images/meme1.png", "images/meme2.png", "images/meme3.png", "images/yosh.png", "images/miller.jpg", "images/meme4.png"];

				for (var i = 0; i < SLOTS_PER_REEL; i ++) {
					var slot = document.createElement('div');
					
					slot.className = 'slot';

					// compute and assign the transform for this slot
					var transform = 'rotateX(' + (slotAngle * i) + 'deg) translateZ(' + REEL_RADIUS + 'px)';

					slot.style.transform = transform;

					// setup the number to show inside the slots
					// the position is randomized to 

			        //var content = $(slot).append('<p>' + ((seed + i)%12)+ '</p>');
			        var content = $(slot).append('<img src = "' + images[i % 6] + '" class = "slotimg">');

					// add the poster to the row
					ring.append(slot);
				}
			}

			function getSeed() {
				// generate random number smaller than 13 then floor it to settle between 0 and 12 inclusive
				return Math.floor(Math.random()*(SLOTS_PER_REEL));
			}
			var oldResults = [0,0,0];

			function spin(timer, args) {
			    //var txt = 'seeds: ';
			    //Originally 6, spins 1-3

			    /*
			    	
			     1. Miller
			     2. berry
			     3. strawberry
			     4. melon
			     5. orange
			     6. yosh
			     
			     0 Miller
			     1 bi wha
			     2 Disney girl
			     3 Iro
			     4 boi what
			     5 yosh
			     6 Miller
			     7 bi wha
			     8 disney girl
			     9 Iro
			     10 boi what
			     11 yosh
			     

			     
			    */
			    //reset();
			    //var results = [1,2,6];
			    var results = args;
			    for(let i = 0; i < 3; i++){
			    	if(oldResults[i] == results[i]){
			    		if(oldResults[i] == 0){
			    			results[i] = 6;
			    		}
			    		else if (oldResults[i] > 5){
			    			results[i] = results[i] - 6;
			    		}
			    		else {
			    			results[i] = results[i] + 6;
			    		}
			    	}
			    }
			    
			    for(let i = 0; i < 6; i++){
			    	oldResults[i] = results[i];
			    }
			    
				for(var i = 1; i < 4; i++) {
					var oldSeed = -1;
					/*
					checking that the old seed from the previous iteration is not the same as the current iteration;
					if this happens then the reel will not spin at all
					*/
					var oldClass = $('#ring'+i).attr('class');
					if(oldClass.length > 4) {
						oldSeed = parseInt(oldClass.slice(10));
						console.log(oldSeed);
					}
					var seed = getSeed();
					while(oldSeed == seed) {
						seed = getSeed();
					}

			        //$('#ring'+i).css('animation','back-spin 1s, spin-' + seed + ' ' + (timer + i*0.5) + 's').attr('class','ring spin-' + seed);
			        //$('#ring'+i).css('animation','front-spin 1s, spin-' + results[i-1] + ' ' + (timer + i*0.5) + 's').attr('class','ring spin-' + results[i-1]);
			        $('#ring'+i).css('animation','back-spin 1s, spin-' + results[i-1] + ' ' + (timer + i*0.5) + 's').attr('class','ring spin-' + results[i-1]);
			        //$('#ring'+i).css('animation','back-spin 1s, spin-' + (results[i-1] + 6) + ' ' + (timer + i*0.5) + 's').attr('class','ring spin-' + (results[i-1] + 6) );
				}

				console.log('=====');
			}

			
			function customSpin(args){
				spin(2,args);
			}
			
			function reset(){
			    var timer = 0;
			    for(var i = 1; i < 4; i ++) {
					var oldSeed = -1;
					/*
					checking that the old seed from the previous iteration is not the same as the current iteration;
					if this happens then the reel will not spin at all
					*/
					var oldClass = $('#ring'+i).attr('class');
					if(oldClass.length > 4) {
						oldSeed = parseInt(oldClass.slice(10));
						console.log(oldSeed);
					}
					var seed = getSeed();
					while(oldSeed == seed) {
						seed = getSeed();
					}

			        //$('#ring'+i).css('animation','back-spin 1s, spin-' + seed + ' ' + (timer + i*0.5) + 's').attr('class','ring spin-' + seed);
			       $('#ring'+i).css('animation','back-spin 0s, spin-' + 0 + ' ' + (timer) + 's').attr('class','ring spin-' + 0);
				}
			    console.log("reset");

			}

			$(document).ready(function() {

				// initiate slots 
			 	createSlots($('#ring1'));
			 	createSlots($('#ring2'));
			 	createSlots($('#ring3'));
			 	createSlots($('#ring4'));
			 	createSlots($('#ring5'));

			 	// hook start button
			 	$('.go').on('click',function(){
			 		var timer = 2;
			 		playGame();
			 		//spin(timer, [1,2,2]);
			 		
			 	})

			 	// hook xray checkbox
			 	$('#xray').on('click',function(){
			 		//var isChecked = $('#xray:checked');
			 		var tilt = 'tiltout';
			 		
			    if($(this).is(':checked')) {
			 			tilt = 'tiltin';
			 			$('.slot').addClass('backface-on');
			 			$('#rotate').css('animation',tilt + ' 2s 1');

						setTimeout(function(){
						  $('#rotate').toggleClass('tilted');
						},2000);
			 		} else {
			      tilt = 'tiltout';
			 			$('#rotate').css({'animation':tilt + ' 2s 1'});

						setTimeout(function(){
				 			$('#rotate').toggleClass('tilted');
				 			$('.slot').removeClass('backface-on');
				 		},1900);
			 		}
			 	})

			 	// hook perspective
			 	$('#perspective').on('click',function(){
			 		$('#stage').toggleClass('perspective-on perspective-off');
			 	})	
			 });
			
			var colors  = [ 'red', '#00ff00', 'blue',  '#6600ff'];
			 
			var current = 0;
		    setInterval(function()
		    {
		        // Update element's style with the new color				
				document.getElementById('spinbutton').style.backgroundColor = colors[current];
				
	
		        // Go to the next color
		        current = (current + 1) % colors.length;
	
		    }, 100);
			
			
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
        </ul>
        <%} else {%>
         <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="Logout">Logout</a>
            </li>
            	<li class="nav-item">
               <a class="nav-link disabled" >Credits:</a>
            </li>
            <li class="nav-item">
               <a class="nav-link disabled" id="usercredits"><%=creditcount%></a>
            </li>
        </ul>
        <%} %>
</nav>

<div class = "game">
	<div id="stage" class="perspective-on">
		<div id = "animation">
			<h1 id = "slottitle">SLOTS</h1>
		</div>
        
        <div id="rotate">
                <div id="ring1" class="ring"></div>
                <div id="ring2" class="ring"></div>
                <div id="ring3" class="ring"></div>
                <!-- <div id="ring4" class="ring"></div>
                <div id="ring5" class="ring"></div> -->
        </div>
        <div id = "buttons">
            <button class="go" style="outline: none;" id = "spinbutton">Spin (20 Credits)</button>
            <!-- <button onclick="reset()">Reset</button>
            <button onclick="customSpin([1,3,6])">Spin2</button> -->
            <h1 id ="creditmessage"></h1>
        </div> 
        <div>
               <!-- <label>
                    <input type="checkbox" id="xray">
                    Show inner workings
                </label>
                <label>
                    <input type="checkbox" id="perspective">
                    Toggle perspective
                </label>  -->
        </div>
    </div>
    
</div>
</body>
</html>
