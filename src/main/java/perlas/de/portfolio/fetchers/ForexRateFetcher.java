package perlas.de.portfolio.fetchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ForexRateFetcher {
	
	@Value("${api.freecurrencyapi.key}")
	private String apiKey ="";
	
	private final static String apiUrl = "https://api.freecurrencyapi.com/v1/latest?apikey=";
	
	@Autowired
	private RestTemplate restTemplate;  //allows to make HTTP requests to  external services
	
	@Autowired
	private ObjectMapper objectMapper;  //used to serialize and deserialize JSON data
	
	
	public double getUsdRate(String currency) {
		
		String urlWithApiKey = apiUrl + apiKey;
		currency = currency.toUpperCase();
		double usdRate = -1.0;
		String json;
		

		ResponseEntity<Object> response = restTemplate.getForEntity(urlWithApiKey, Object.class);
		
		try {
			json = objectMapper.writeValueAsString(response.getBody());
			
			JsonNode node = objectMapper.readTree(json);
			JsonNode dataNode = node.get("data");
			
			usdRate = dataNode.get(currency).asDouble();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 1 / usdRate;
		
		
		
	}
	

}
