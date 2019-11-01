package parsing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
    /**
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws FormatException 
     * @see HttpServlet#HttpServlet()
     */
	
    public MyServlet() throws FormatException, FileNotFoundException, IOException {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Error: City Not Found");
		
		// search for a city using name
		if(request.getParameter("citySearch") != null && request.getParameter("citySearch").trim().length() != 0) {
			
			// Open the file locally
			URL urlH = getClass().getResource("/../../city.list.json");
			BufferedReader br = new BufferedReader(new InputStreamReader(urlH.openStream()));
			
			// Convert the JSON to an ArrayList
			Type arrayListType = new TypeToken<ArrayList<OpenWeatherCityFromIDList>>() {}.getType();
			ArrayList<OpenWeatherCityFromIDList> allCities = new Gson().fromJson(br, arrayListType);
			
			// Search through allCities to find any cities that match the current city name
			ArrayList<OpenWeatherCity> cityList = new ArrayList<OpenWeatherCity>();
			for(OpenWeatherCityFromIDList city: allCities) {
				if(city.getName().equals(request.getParameter("citySearch").trim())) {
					// Retrieve that city's weather info from weather API
					URL url = new URL("https://api.openweathermap.org/data/2.5/weather?id=" + city.getId() + "&units=imperial&appid=4d7c3ba376468b7b30ed4479c862c106");
				    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				    connection.setRequestMethod("GET");
				    connection.connect();
				
				    // Convert from JSON
				    BufferedReader json  = new BufferedReader(new InputStreamReader(connection.getInputStream()));          
				    Gson gson = new Gson();
				    OpenWeatherCity currentCity = gson.fromJson(json, OpenWeatherCity.class);
				    cityList.add(currentCity);
				}
			}
						
			// Append to the current session
			HttpSession session = request.getSession();
			session.setAttribute("cityList", cityList);
			String nextPage = "all.jsp";
			
			// Check if only 1 city is returned
			if(cityList.size() == 1)
				nextPage = "results.jsp";
			
		    // Forward to results
			RequestDispatcher rd = request.getRequestDispatcher(nextPage);
			rd.forward(request, response);
			return;
		}
		
		// search for a city using latitude and longitude
		else if (request.getParameter("latSearch") != null && request.getParameter("longSearch") != null ) {
			URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + request.getParameter("latSearch") + "&lon=" + request.getParameter("longSearch") + "&units=imperial&appid=4d7c3ba376468b7b30ed4479c862c106");
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.connect();
		
		    BufferedReader json  = new BufferedReader(new InputStreamReader(connection.getInputStream()));          
		
		    Gson gson = new Gson();
		    OpenWeatherCity currentCity = gson.fromJson(json, OpenWeatherCity.class);
		    
		    ArrayList<OpenWeatherCity> cityList = new ArrayList<OpenWeatherCity>();
		    cityList.add(currentCity);
			
		    HttpSession session = request.getSession();
		    session.setAttribute("cityList", cityList);
		 			
		    
			RequestDispatcher rd = request.getRequestDispatcher("results.jsp");
			rd.forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
