package Weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MainProgram {
	private static Map<String, WeatherLocation> locations = new HashMap<String, WeatherLocation>();
	
	private static boolean readDataErrorChecking(Scanner userInput) {
		// Create a file and check if it exists
		String filename = userInput.nextLine();
		File inputFile = new File(filename);
		
		// Does the main data filling operations
		try {
			BufferedReader parseFile = new BufferedReader(new FileReader(inputFile));
			String nextLine = parseFile.readLine();
			while(nextLine != null) {
				StringTokenizer tokens = new StringTokenizer(nextLine, "//|");
			
				//Get all the data to make one WeatherLocation
				String city, state, country, conditionN;
				int currentTemperature, dayLow, dayHigh, humidity, windDirection;
				float pressure, visibility, windspeed, latitude, longitude;
				long sunrise, sunset;
				ArrayList<String> descriptions = new ArrayList<String>();
				
				// Instantly quits if there aren't enough tokens on the line to make a valid line
				if(tokens.countTokens() < 16) {
					response.sendRedirect("FileError.jsp"); 
					return false;
				}
				
				// Error Checking?
				try {
					city = tokens.nextToken();
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
				catch (NoSuchElementException | NumberFormatException ex) {
					errorPrintout(filename, "malformed");
					return false;
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
				locations.put(city.toLowerCase(), new WeatherLocation(city, currentTemperature, dayLow, dayHigh, 
						humidity, pressure, visibility, windspeed, windDirection, descriptions));
				
				// Set up input for next iteration
				nextLine = parseFile.readLine();
			}
			parseFile.close();
			return true;
		}
		
		// Catches when the file isn't found
		catch (FileNotFoundException fnfe) {
			errorPrintout(filename, "read");
			return false;
		}
		
		
		// Catches when the file isn't found
		catch (IOException ioe) {
			errorPrintout(filename, "read");
			return false;
		}
	}
	
	private static void errorPrintout(String filename, String problem) {
		if(problem.equals("read")) {
			System.out.println("The file " + filename + " could not be read.\n");
			System.out.print("What is the name of the weather file? ");
		}
		else if(problem.equals("malformed")) {
			System.out.println("The file " + filename + " is not formatted properly.\n");
			System.out.print("What is the name of the weather file? ");
		}
		else if(problem.equals("city")) {
			System.out.println("Unable to find city " + filename + ".\n");
		}
		else if(problem.equals("choice")) {
			System.out.println("That is not a valid option.\n");
			System.out.print("What information would you like to know? ");
		}
	}
	
	private static String newCity(Scanner userInput){
		boolean validCity = false;
		String currentCity = "";
		while(!validCity) {
			System.out.print("For what city would you like weather information? ");
			currentCity = userInput.nextLine();
			
			if(currentCity.equalsIgnoreCase("exit")) {
				System.out.println("Thank you for using my weather program.");
				userInput.close();
				System.exit(0);
			}
			else if(!locations.containsKey(currentCity.toLowerCase()) && !currentCity.equalsIgnoreCase("all")) {
				errorPrintout(currentCity, "city");
			}
			else
				validCity = true;
		}
		
		// Proper printout
		System.out.println("I do have information about the weather in " + 
				(currentCity.equalsIgnoreCase("all") ? "all cities.\n" : locations.get(currentCity).getCityName() + ".\n"));
		System.out.println("\t1) Temperature");
		System.out.println("\t2) High and low temperature today");
		System.out.println("\t3) Humidity");
		System.out.println("\t4) Pressure");
		System.out.println("\t5) Visibility");
		System.out.println("\t6) Wind speed and direction");
		System.out.println("\t7) Descriptions of weather conditions");
		System.out.println("\t8) Everything");
		System.out.println("\t9) Enter a different city");
		System.out.println();
		
		return currentCity;
	}
	
	public static void main(String []args) {
		// Scanner to read all user input
		Scanner userInput = new Scanner(System.in);
		
		System.out.print("What is the name of the weather file? ");
		
		// Keep asking the user for a file name until they enter the right file name
		while(!readDataErrorChecking(userInput)) {}		
		
		// Get the current city the options pertain to
		System.out.println();
		String currentCity = newCity(userInput);
		
		while(true) {
			System.out.print("What information would you like to know? ");
			String choice = userInput.nextLine();
			
			// Switch on the user input
			switch (choice) {
				case "1": 
					if(currentCity.equalsIgnoreCase("all")) {
						System.out.println();
						locations.forEach((k,v) -> System.out.println(v.getTemperature()));
						System.out.println();
					}
					else
						System.out.println("\n" + locations.get(currentCity).getTemperature());
					break;
				case "2":
					if(currentCity.equalsIgnoreCase("all")) {
						System.out.println();
						locations.forEach((k,v) -> System.out.println(v.getHighAndLowTemp()));
						System.out.println();
					}
					else
						System.out.println("\n" + locations.get(currentCity).getHighAndLowTemp());
					break;
				case "3":
					if(currentCity.equalsIgnoreCase("all")) {
						System.out.println();
						locations.forEach((k,v) -> System.out.println(v.getHumidity()));
						System.out.println();
					}
					else
						System.out.println("\n" + locations.get(currentCity).getHumidity());
					break;
				case "4":
					if(currentCity.equalsIgnoreCase("all")) {
						System.out.println();
						locations.forEach((k,v) -> System.out.println(v.getPressure()));
						System.out.println();
					}
					else
						System.out.println("\n" + locations.get(currentCity).getPressure());
					break;
				case "5":
					if(currentCity.equalsIgnoreCase("all")) {
						System.out.println();
						locations.forEach((k,v) -> System.out.println(v.getVisibility()));
						System.out.println();
					}
					else
						System.out.println("\n" + locations.get(currentCity).getVisibility());
					break;
				case "6":
					if(currentCity.equalsIgnoreCase("all")) {
						System.out.println();
						locations.forEach((k,v) -> System.out.println(v.getWindSpeed()));
						System.out.println();
					}
					else
						System.out.println("\n" + locations.get(currentCity).getWindSpeed());
					break;
				case "7":
					if(currentCity.equalsIgnoreCase("all")) {
						System.out.println();
						locations.forEach((k,v) -> System.out.println(v.getWeatherDescription()));
						System.out.println();
					}
					else
						System.out.println("\n" + locations.get(currentCity).getWeatherDescription());
					break;
				case "8":
					if(currentCity.equalsIgnoreCase("all")) {
						System.out.println();
						locations.forEach((k,v) -> System.out.println(v.getAllData()));
						System.out.println();
					}
					else
						System.out.println("\n" + locations.get(currentCity).getAllData());
					break;
				case "9": 
					currentCity = newCity(userInput);
					break;
				default: 
					errorPrintout(choice, "choice");
					break;
			}
		}
	}
}
