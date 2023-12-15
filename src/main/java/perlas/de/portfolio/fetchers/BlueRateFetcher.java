package perlas.de.portfolio.fetchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
/**
 * In charge of retrieving the average blue rate from bluelytics api.
 * Makes the request to the api's url, serialize the json and returns
 * the latest value for the argentinian "dollar blue" or returns -1 if catchs an exception
 */
public class BlueRateFetcher {
	
	private final static String API_URL = "https://api.bluelytics.com.ar/v2/latest";
	
	
	@Autowired
	private RestTemplate restTemplate;  //allows to make HTTP requests to  external services
	
	@Autowired
	private ObjectMapper objectMapper;  //used to serialize and deserialize JSON data
	
	
	public double getBlueRate() {
		
		// Send the GET request to external service (apiURL)
		ResponseEntity<Object> response = restTemplate.getForEntity(API_URL, Object.class);
		String json;
		double blueRate = -1.0;
		
		try {
			json = objectMapper.writeValueAsString(response.getBody()); //get json as a string
			
			JsonNode node = objectMapper.readTree(json); // allows to navigate through the json structure
			JsonNode blueNode = node.get("blue");  // extract the "blue" node of the json data
			
			blueRate = blueNode.get("value_avg").asDouble(); //get the value_avg and parse it as a double
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1 / blueRate;
		
	}

}
