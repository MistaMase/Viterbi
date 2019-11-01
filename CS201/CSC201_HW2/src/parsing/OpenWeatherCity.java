package parsing;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class OpenWeatherCity {
	private Coord coord;
	private Main main;
	private Wind wind;
	private String name;
	private Sys sys;
	private int id;
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public double getLat() {
		return coord.getLat();
	}
	
	public double getLon() {
		return coord.getLon();
	}
	
	public void setLat(double lat) {
		coord.setLat(lat);
	}
	
	public void setLon(double lon) {
		coord.setLon(lon);
	}
	
	public double getTemp() {
		return main.getTemp();
	}
	
	public double getTemp_Max() {
		return main.getTemp_max();
	}
	
	public double getTemp_Min() {
		return main.getTemp_min();
	}
	
	public int getHumidity() {
		return main.getHumidity();
	}
	
	public void setTemp(double temp) {
		main.setTemp(temp);
	}
	
	public void setTemp_Max(double temp_max) {
		main.setTemp_max(temp_max);
	}
	
	public void setTemp_Min(double temp_min) {
		main.setTemp_min(temp_min);
	}
	
	public void setHumidity(int humidity) {
		main.setHumidity(humidity);
	}
	
	public double getWindSpeed() {
		return wind.getSpeed();
	}
	
	public void setWindSpeed(double speed) {
		wind.setSpeed(speed);
	}
	
	public String getCountry() {
		return sys.getCountry();
	}
	
	public String getName() {
		return name;
	}
	
	public void setCountry(String country) {
		sys.setCountry(country);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getSunset() {
		return sys.getSunrise();
	}
	
	public long getSunrise() {
		return sys.getSunset();
	}
	
	public void setSunset(long sunset) {
		sys.setSunset(sunset);
	}
	
	public void setSunrise(long sunrise) {
		sys.setSunrise(sunrise);
	}
	
}

class Coord {

	private Double lon;
	private Double lat;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
	
class Main {
	private double temp;
	private int humidity;
	private double temp_min;
	private double temp_max;
	
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public double getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}
	public double getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}
}

class Wind {
	private double speed;
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}

class Sys {
	private String country;
	private long sunrise;
	private long sunset;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public long getSunrise() {
		return sunrise;
	}
	
	public long getSunset() {
		return sunset;
	}
	
	public void setSunrise(long sunrise) {
		this.sunrise = sunrise;
	}
	
	public void setSunset(long sunset) {
		this.sunset = sunset;
	}
}