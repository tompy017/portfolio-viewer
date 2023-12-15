package perlas.de.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import perlas.de.portfolio.entities.Investment;
import perlas.de.portfolio.service.InvestmentService;

/**
 * Dedicated to handling price updates for a given 
 * Investments based on its type and currency.
 */


@Controller
@RequestMapping("investments/update-price")
public class UpdatePriceController {
	
	@Autowired
	private InvestmentService investmentService;
	
	@GetMapping("/{id}")
	public String updateInvestmentPrice(@PathVariable int id) {
		
		Investment investment = investmentService.getInvestmentById(id);	
		double newFetchedPrice = investmentService.fetchInvestmentPriceInUsd(investment);

		if (newFetchedPrice > 0) {
			
			if (investment.getCategory().equalsIgnoreCase("crypto") 
					&& !investment.getType().equalsIgnoreCase("stable-coin")) {
				
				investment.setActualPrice(newFetchedPrice);
				investment.setActualPriceInUSD(investment.getQty() * newFetchedPrice);
								
			} else if (investment.getCurrency().equalsIgnoreCase("usd")) {
				
				investment.setActualPriceInUSD(investment.getQty() * 1.0);				
				
			} else if (investment.getCurrency().equalsIgnoreCase("ars")) {
				
				investment.setActualPriceInUSD((investment.getActualPrice() * newFetchedPrice) * investment.getQty());
				
				if (investment.getType().equalsIgnoreCase("cash")) {
					investment.setActualPrice(newFetchedPrice);
				}
			
			} else {
				if (investment.getType().equalsIgnoreCase("cash")) {
					investment.setActualPrice(newFetchedPrice);
				}
				
				investment.setActualPriceInUSD((investment.getQty() * investment.getActualPrice()) * newFetchedPrice);
			}
		}
		
		investmentService.updateInvestment(investment);
			
		return "redirect:/investments/";
	}
}
