package perlas.de.portfolio;


import perlas.de.portfolio.fetchers.CryptoPriceFetcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CryptoPriceFetcherTest {

    @Autowired
    private CryptoPriceFetcher cryptoPriceFetcher;

    @Test
    public void testGetActualValueInUSDForBtc() {
        double bitcoinPrice = cryptoPriceFetcher.getActualValueInUSD("bitcoin");
        System.out.println("\n\n *** BITCOIN PRICE: " + bitcoinPrice + " ***\n\n");
        assertThat(bitcoinPrice).isGreaterThan(0);
    }
    
    
    @Test
    public void testGetActualValueInUSDForEth() {
        double ethPrice = cryptoPriceFetcher.getActualValueInUSD("ethereum");
        System.out.println("\n\n *** ETHEREUM PRICE: " + ethPrice + " ***\n\n");
        assertThat(ethPrice).isGreaterThan(0);
    }
    
    @Test
    public void testGetActualValueInUSDForIota() {
    	double iotaPrice = cryptoPriceFetcher.getActualValueInUSD("iota");
    	System.out.println("\n\n *** IOTA PRICE: " + iotaPrice + " ***\n\n");
        assertThat(iotaPrice).isGreaterThan(0);
    	
    }
}