package backend;

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
	private String state;
	private String country;
	private float latitude;
	private float longitude;
	private long sunrise;
	private long sunset;
	
	
	// Intentionally new default constructor
	public WeatherLocation(String city, String state, String country, float latitude, float longitude, long sunrise, long sunset, int currentTemperature, int dayLow, int dayHigh, int humidity, float pressure, float visibility, float windspeed, int windDirection, ArrayList<String> conditionDescription) {
		this.city = city;
		this.state = state;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sunrise = sunrise;
		this.sunset = sunset;
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
	
	public int getTemperature() {
		return currentTemperature;
	}

	public int getDayLow() {
		return dayLow;
	}

	public int getDayHigh() {
		return dayHigh;
	}

	public int getHumidity() {
		return humidity;
	}

	public float getPressure() {
		return pressure;
	}

	public float getVisibility() {
		return visibility;
	}

	public float getWindspeed() {
		return windspeed;
	}

	public int getWindDirection() {
		return windDirection;
	}

	public ArrayList<String> getConditionDescription() {
		return conditionDescription;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public long getSunrise() {
		return sunrise;
	}

	public long getSunset() {
		return sunset;
	}
	
	

}
