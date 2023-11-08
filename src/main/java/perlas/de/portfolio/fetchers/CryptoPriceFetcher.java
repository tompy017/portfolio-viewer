package perlas.de.portfolio.fetchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CryptoPriceFetcher {
	
	private final String apiUrl = "https://api.coingecko.com/api/v3/simple/price";
	
	@Autowired
	private RestTemplate restTemplate;  //allows to make HTTP requests to  external services
	
	@Autowired
	private ObjectMapper objectMapper;  //used to serialize and deserialize JSON data
	
	
	public double getActualValueInUSD(String tokenName) {
		
		String json;
		double actualValue = -1.0;
		String urlWithTokenName = apiUrl + "?ids=" + tokenName + "&vs_currencies=usd";
		
		ResponseEntity<Object> response = restTemplate
				.getForEntity(urlWithTokenName, Object.class);
		
		try {
			json = objectMapper.writeValueAsString(response.getBody());
			
			JsonNode node = objectMapper.readTree(json); //navigate through the json structure
			
			actualValue = node.get(tokenName).get("usd").asDouble();

		} catch (JsonProcessingException e){
			e.printStackTrace();
			
		}
		
		return actualValue;

	}
	


}
