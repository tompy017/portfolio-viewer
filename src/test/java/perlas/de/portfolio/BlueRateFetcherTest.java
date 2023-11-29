package perlas.de.portfolio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import perlas.de.portfolio.fetchers.BlueRateFetcher;

@SpringBootTest
public class BlueRateFetcherTest {
	
	@Autowired
	private BlueRateFetcher blueRateFetcher;
	
	@Test
	public void testGetBlueRate() {
		
		double blue = blueRateFetcher.getBlueRate();
		
		System.out.println("\n\n *** BLUE RATE: " + blue + " ***\n\n");
		assertThat(blue).isGreaterThan(0);
	}

}
