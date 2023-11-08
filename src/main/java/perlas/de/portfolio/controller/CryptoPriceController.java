package perlas.de.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import perlas.de.portfolio.fetchers.CryptoPriceFetcher;

@RestController
@RequestMapping("/api/crypto")
public class CryptoPriceController {

	@Autowired
	private CryptoPriceFetcher cryptoPriceFetcher;
	
	
	@GetMapping("/actual-price/{tokenName}")
	public double getPrice(@PathVariable String tokenName) {
		return cryptoPriceFetcher.getActualValueInUSD(tokenName);
	}
}
