package MyPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/ForecastServlet")
public class ForecastServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the city from the request
        String city = request.getParameter("city");
        
        // Your OpenWeatherMap API key
        String apiKey = "YOUR_API_KEY";
        
        // Construct the URL for the forecast API
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey + "&units=metric";
        
        // Initialize variables to store forecast data
        StringBuilder responseContent = new StringBuilder();
        String forecastData = null;

        // Connect to the API and retrieve forecast data
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read response from the API
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            Scanner scanner = new Scanner(reader);

            while (scanner.hasNext()) {
                responseContent.append(scanner.nextLine());
            }

            scanner.close();
            connection.disconnect();

            forecastData = responseContent.toString();
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
            forecastData = null;
        }

        // Set forecast data as request attribute
        request.setAttribute("forecastData", forecastData);

        // Forward the request to the JSP page for rendering
        request.getRequestDispatcher("forecast.jsp").forward(request, response);
    }
}
