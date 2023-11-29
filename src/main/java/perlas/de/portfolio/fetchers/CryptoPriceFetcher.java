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
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public double getActualValueInUSD(String tokenName) {
    	
        String urlWithTokenName = apiUrl + "?ids=" + tokenName + "&vs_currencies=usd";

        ResponseEntity<String> responseEntity = restTemplate.
        		getForEntity(urlWithTokenName, String.class);

        try {
        	
        	
        	JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode priceNode = jsonNode.get(tokenName).get("usd");
            
            return priceNode.asDouble();
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

}
