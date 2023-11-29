package perlas.de.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import perlas.de.portfolio.fetchers.ForexRateFetcher;

@SpringBootTest
public class ForexRateFetcherTest {
	
	@Autowired
	private ForexRateFetcher forexRateFetcher;
	
	
	@Test
	public void testGetUsdRate() {
		
		String currency = "eur";
		
		double usdRate = forexRateFetcher.getUsdRate(currency);
		
		System.out.println("\n\n *** EURO RATE " + usdRate + " ***\n\n");
		
		assertEquals(true, usdRate >= 0.0);
		
		
		
	}

}
