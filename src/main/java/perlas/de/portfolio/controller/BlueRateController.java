package perlas.de.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import perlas.de.portfolio.fetchers.BlueRateFetcher;

@RestController
@RequestMapping("/api/exchange-rates")
public class BlueRateController {
	
	@Autowired
	private BlueRateFetcher blueRateFetcher;
	
	
	@GetMapping("/blue-avg")
	public double getBlueRate() {
		return blueRateFetcher.getBlueRate();
	}
	

}
