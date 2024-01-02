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
			
//			CRYPTO NON STABLE
			if (isCryptoNonStableCoin(investment)) {
				investment.setActualPrice(newFetchedPrice);
				investment.setActualValueInUSD(investment.getQty() * newFetchedPrice);
			
//			USD CURRENCY AND STABLE	
			} else if (isUsdCurrency(investment)) {
				investment.setActualValueInUSD(investment.getQty());				
				
//			ARS CURRENCY	
			} else if (isArsCurrency(investment)) {
//				CASH
				if (isCashType(investment)) {
					investment.setActualPrice(newFetchedPrice);
					investment.setActualValueInUSD(investment.getQty() * investment.getActualPrice());
//				NO CASH
				} else {
					investment.setActualValueInUSD(investment.getActualPrice() * investment.getQty() * newFetchedPrice);
				}

//			OTHER CURRENCY
			} else {
//				CASH
				if (isCashType(investment)) {
					investment.setActualPrice(newFetchedPrice);
				}
				
				investment.setActualValueInUSD((investment.getQty() * investment.getActualPrice()) * newFetchedPrice);
			}
		}
		
		investmentService.updateInvestment(investment);
			
		return "redirect:/investments/";
	}
	
	
	
	private boolean isCryptoNonStableCoin(Investment investment) {
		
		return investment.getCategory().equalsIgnoreCase("crypto")
				&& !investment.getType().equalsIgnoreCase("stable-coin");
	}
	
	private boolean isUsdCurrency(Investment investment) {
		return investment.getCurrency().equalsIgnoreCase("usd");
	}
	
	private boolean isArsCurrency(Investment investment) {
		return investment.getCurrency().equalsIgnoreCase("ars");
	}
	
	private boolean isCashType(Investment investment) {
		return investment.getType().equalsIgnoreCase("cash");
	}
	
}
