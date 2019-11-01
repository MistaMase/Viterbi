package Weather;

import java.util.ArrayList;

public class WeatherLocation {
	private String city;
	private int currentTemperature;
	private int dayLow;
	private int dayHigh;
	private int humidity;
	private float pressure;
	private float visibility;
	private float windspeed;
	private int windDirection;
	private ArrayList<String> conditionDescription;
	
	
	// Intentionally new default constructor
	public WeatherLocation(String city, int currentTemperature, int dayLow, int dayHigh, int humidity, float pressure, float visibility, float windspeed, int windDirection, ArrayList<String> conditionDescription) {
		this.city = city;
		this.currentTemperature = currentTemperature;
		this.dayLow = dayLow;
		this.dayHigh = dayHigh;
		this.humidity = humidity;
		this.pressure = pressure;
		this.visibility = visibility;
		this.windspeed = windspeed;
		this.windDirection = windDirection;
		this.conditionDescription = conditionDescription;
	}
	
	public String getCityName() {
		return city;
	}
	
	public String getTemperature() {
		return "The temperature in " + city + " is " + currentTemperature + " degrees Fahrenheit.\n";
	}
	
	public String getHighAndLowTemp() {
		String temp = "The high temperature in " + city + " is " + dayHigh + " degrees Fahrenheit.\n";
		temp += "The low temperature in " + city + " is " + dayLow + " degrees Fahrenheit.\n";
		return temp;
	}
	
	public String getHumidity() {
		return "The humidity in " + city + " is " + humidity + "%.\n";
	}
	
	public String getPressure() {
		return "The pressure in " + city + " is " + pressure + " Inch Hg.\n";
	}
	
	public String getVisibility() {
		return "The visibility in " + city + " is " + visibility + " miles.\n";
	}
	
	public String getWindSpeed() {
		String ws =  "The wind speed in " + city + " is " + windspeed + " miles/hour.\n";
		ws += "The wind direction in " + city + " is " + windDirection + " degrees.\n";
		return ws;
	}
	
	public String getWeatherDescription() {
		String desc = city + " weather can be described as " + conditionDescription.get(0);
		for(int i = 1; i < conditionDescription.size(); i++) {
			if(conditionDescription.size() - 1 == i)
				// Why you say no oxford comma?
				desc += " and " + conditionDescription.get(i);
			else
				desc += ", " + conditionDescription.get(i);
		}
		desc += ".\n";
		return desc;
	}
	
	public String getAllData() {
		String info = getTemperature();
		info += getHighAndLowTemp();
		info += getHumidity();
		info += getPressure();
		info += getVisibility();
		info += getWindSpeed();
		info += getWeatherDescription();
		return info;
	}

}
