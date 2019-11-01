package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.WeatherLocation;

/**
 * Servlet implementation class FileParser
 */
@WebServlet("/FileParser")
public class FileParser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, WeatherLocation> locations = new HashMap<String, WeatherLocation>();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileParser() {
        super();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
		RequestDispatcher requestDispatcherInvalid = request.getRequestDispatcher("/FileError.jsp");
		RequestDispatcher requestDispatcherValid = request.getRequestDispatcher("/index.jsp");

		
		// Create a file and check if it exists
     	String filename = "weather.txt";
     	File inputFile = new File(filename);
     		
     	// Does the main data filling operations
     	try {
     		BufferedReader parseFile = new BufferedReader(new FileReader(inputFile));
     		String nextLine = parseFile.readLine();
     		while(nextLine != null) {
     			StringTokenizer tokens = new StringTokenizer(nextLine, "//|");
     			
     			//Get all the data to make one WeatherLocation
     			String city="", state="", country="", conditionN="";
     			int currentTemperature=0, dayLow=0, dayHigh=0, humidity=0, windDirection=0;
     			float pressure=0, visibility=0, windspeed=0, latitude=0, longitude=0;
     			ArrayList<String> descriptions = new ArrayList<String>();
     			long sunrise=0, sunset=0;
     				
     			// Instantly quits if there aren't enough tokens on the line to make a valid line
     			if(tokens.countTokens() < 10) {
     		        requestDispatcherInvalid.forward(request, response);
     			}
     				
     			// Error Checking?
     			try {
     				city = tokens.nextToken();
     				state = tokens.nextToken();
     				country = tokens.nextToken();
     				latitude = Float.parseFloat(tokens.nextToken());
     				longitude = Float.parseFloat(tokens.nextToken());
     				sunrise = Long.parseLong(tokens.nextToken());
     				sunset = Long.parseLong(tokens.nextToken());
     				currentTemperature = Integer.parseInt(tokens.nextToken());
     				dayLow = Integer.parseInt(tokens.nextToken());
     				dayHigh = Integer.parseInt(tokens.nextToken());
     				humidity = Integer.parseInt(tokens.nextToken());
     				pressure = Float.parseFloat(tokens.nextToken());
     				visibility = Float.parseFloat(tokens.nextToken());
     				windspeed = Float.parseFloat(tokens.nextToken());
     				windDirection = Integer.parseInt(tokens.nextToken());
     				descriptions.add(tokens.nextToken());
     			}
     			catch (Exception ex) {
     		        requestDispatcherInvalid.forward(request, response);
     			}
     				
     			// Continue adding multiple descriptor tokens
     			try {
     				// Loops until there's an exception
     				while(true) {
     					conditionN = tokens.nextToken();
     					descriptions.add(conditionN);
     				}
     			}
     			catch (NoSuchElementException nsee) {
     				//Working correctly for this to trigger
     			}
     				
     			// Add this new locations to the weather map database
     			locations.put(city.toLowerCase(), new WeatherLocation(city, state, country, latitude, longitude, sunrise, sunset, currentTemperature, dayLow, dayHigh, 
     						humidity, pressure, visibility, windspeed, windDirection, descriptions));
     				
     				// Set up input for next iteration
     				nextLine = parseFile.readLine();
     			}
     			parseFile.close();
     		}
     		
     		// Catches when the file isn't found
     		catch (Exception ex) {
     	        requestDispatcherInvalid.forward(request, response);
     		}
        requestDispatcherValid.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
