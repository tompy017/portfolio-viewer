package perlas.de.portfolio.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import perlas.de.portfolio.entities.Investment;
import perlas.de.portfolio.fetchers.BlueRateFetcher;
import perlas.de.portfolio.fetchers.CryptoPriceFetcher;
import perlas.de.portfolio.fetchers.ForexRateFetcher;
import perlas.de.portfolio.repositories.InvestmentRepository;

@Service
public class InvestmentServiceImpl implements InvestmentService {

	@Autowired
	private InvestmentRepository investmentRepository;

	@Autowired
	private BlueRateFetcher blueRateFetcher;

	@Autowired
	private CryptoPriceFetcher cryptoPriceFetcher;

	@Autowired
	private ForexRateFetcher forexRateFetcher;
	
	private Logger logger = LoggerFactory.getLogger(InvestmentServiceImpl.class);

	@Override
	public Investment saveInvestment(Investment investment) {

		return investmentRepository.save(investment); // this will be shown in postman
	}

	@Override
	public List<Investment> getAllInvestments() {

		return investmentRepository.findAll();
	}

	public Investment getInvestmentById(int id) {

		return investmentRepository.findById(id).orElse(null);
	}

	@Override
	public Investment updateInvestment(Investment investment) {

		Investment investmentToUpdate = investmentRepository.findById(investment.getId()).orElse(null);

		if (investmentToUpdate != null) {

			if (investment.getCategory() != null) {
				investmentToUpdate.setCategory(investment.getCategory());
			}
			if (investment.getName() != null) {
				investmentToUpdate.setName(investment.getName());
			}
			if (investment.getPrice() >= 0.0) {
				investmentToUpdate.setPrice(investment.getPrice());
			}
			
			if (investment.getPurchasedDate() != null) {
				investmentToUpdate.setPurchasedDate(investment.getPurchasedDate());
			}
			if (investment.getQty() >= 0.0) {
				investmentToUpdate.setQty(investment.getQty());
			}
			if (investment.getToken() != null) {
				investmentToUpdate.setToken(investment.getToken());
			}
			if (investment.getType() != null) {
				investmentToUpdate.setType(investment.getType());
			}

			if (investment.getCurrency() != null) {
				investmentToUpdate.setCurrency(investment.getCurrency());
			}
			
			if (investment.getActualPrice() >= 0.0) {
				investmentToUpdate.setActualPrice(investment.getActualPrice());
			}

			investmentRepository.save(investmentToUpdate);

			return investmentToUpdate;
		}

		return null; // if no investment was found just return null
	}

	@Override
	public String deleteInvestment(int id) {

		if (investmentRepository.existsById(id)) {
			investmentRepository.deleteById(id);
			return "DELETED";

		} else {
			return "ID NOT EXISTS";
		}
	}

	@Override
	public List<Investment> listInvestmentByType(String type) {
		List<Investment> investmentsByType = investmentRepository.findAll().stream()
				.filter(investment -> investment.getType().equalsIgnoreCase(type)).collect(Collectors.toList());

		return investmentsByType;
	}

	@Override
	public List<Investment> listInvestmentByCategory(String category) {
		List<Investment> investmentByCategory = investmentRepository.findAll().stream()
				.filter(investment -> investment.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
		return investmentByCategory;
	}

	@Override
	public List<Investment> listInvestmentByToken(String token) {
		List<Investment> investmentByToken = investmentRepository.findAll().stream()
				.filter(investment -> investment.getToken().equalsIgnoreCase(token)).collect(Collectors.toList());

		return investmentByToken;
	}

	@Override
	public Set<String> getAllInvestmentTypes() {
		Set<String> types = new HashSet<>();

		// get unique investment types
		for (Investment investment : investmentRepository.findAll()) {
			types.add(investment.getType());
		}

		return types;
	}

	@Override
	public Set<String> getAllInvestmentCategories() {

		Set<String> categories = new HashSet<>();

		// get unique category types
		for (Investment investment : investmentRepository.findAll()) {
			categories.add(investment.getCategory());
		}

		return categories;
	}

	@Override
	public List<Investment> listInvestentByCurrency(String currency) {

		List<Investment> investmentsByCurrency = investmentRepository.findAll().stream()
				.filter(investment -> investment.getCurrency().equalsIgnoreCase(currency)).collect(Collectors.toList());

		return investmentsByCurrency;
	}

	
	@Override
	public double getInvestmentValueInUsd(Investment investment) {
		
		double valueInUsd = investment.getActualPrice() * investment.getQty();
		
		
		return valueInUsd;
	}
	

	
	@Override
	public double getPortfolioValueInUsd() {
		
		double total = 0;
		
		for (Investment investment : this.getAllInvestments()) {
			total += this.getInvestmentValueInUsd(investment);
		}
		
		return total;
	}
	
	
	@Override
	/**
	 * Calculates the total value in usd for a given category.
	 * @return total value in usd for the given category
	 */
	public double getTotalValueInUsdByCategory(String category) {

		double totalUsdValue = 0.0;

		for (Investment investment : this.listInvestmentByCategory(category)) {
			totalUsdValue += this.getInvestmentValueInUsd(investment);
		}
	

		return formatDoubleWithTwoDecimals(totalUsdValue);
	}
	
	
	@Override
	/**
	 * Calculates the total value in usd for a given type.
	 * @return total value in usd for the given type
	 */
	public double getTotalValueInUsdByType(String type) {

		double totalUsdValue = 0.0;

		for (Investment investment : this.listInvestmentByType(type)) {
			totalUsdValue += this.getInvestmentValueInUsd(investment);
		}

		return formatDoubleWithTwoDecimals(totalUsdValue);
	}

	
	@Override
	/**
	 * Calculates the % of portfolio this type has (against other types)
	 * @return the porcentaje of this type in comparison to other types
	 */
	public double getTotalPercentageOfType(String type) {
		
		double portfolioTotal = this.getPortfolioValueInUsd();
		double typeTotal = 0;
		
		for (Investment investment : this.listInvestmentByType(type)) {
			double investmentTotal = this.getInvestmentValueInUsd(investment);
			typeTotal += investmentTotal;
		}
		
		double percentage = (typeTotal / portfolioTotal) * 100;
		
		return formatDoubleWithTwoDecimals(percentage);
	}
	
	
	@Override
	/**
	 * Calculates the % of portfolio this category has (against other categories)
	 * @return the porcentaje of this category in comparison to the other categories
	 */
	public double getTotalPercentageOfCategory(String category) {
		
		double portfolioTotal = this.getPortfolioValueInUsd();
		double categoryTotal = 0;
		
		for (Investment investment : this.listInvestmentByCategory(category)) {
			double investmentTotal = this.getInvestmentValueInUsd(investment);
			
			categoryTotal += investmentTotal;
		}
		
		double percentage = (categoryTotal / portfolioTotal) * 100;

		
		return formatDoubleWithTwoDecimals(percentage);
	}
	
	
	
	
	@Override
	/**
	 * Gathers the totalValueInUsdByCategory and totalPercentageOfCategory into a map.
	 * @return a Map containing each category and a list cointaining the total and the percentage that category represents.
	 * "category" : [totalValueInUsd, percentageOfCategory]
	 */
	public Map<String, List<Double>> getTotalsPerCategory() {

		Map<String, List<Double>> totalsPerCategory = new HashMap<>();

		for (String category : this.getAllInvestmentCategories()) {
			
			double total = this.getTotalValueInUsdByCategory(category);
			double percentage = this.getTotalPercentageOfCategory(category);
			
			List<Double> values = new ArrayList<>();
			values.add(total);
			values.add(percentage);

			totalsPerCategory.put(category, values);
		}

		return totalsPerCategory;
	}

	
	
	
	@Override
	/**
	 * Gathers the totalValueInUsdByType and totalPercentageOfType into a map.
	 * @return a Map containing each type and a list cointaining the total and the percentage that type represents.
	 * "category" : [totalValueInUsd, percentageOfCategory]
	 */
	public Map<String, List<Double>> getTotalsPerType() {

		Map<String, List<Double>> totalsPerType = new HashMap<>();

		for (String type : this.getAllInvestmentTypes()) {
			
			double total = this.getTotalValueInUsdByType(type);
			double percentage = this.getTotalPercentageOfType(type);
			
			List<Double> values = new ArrayList<>();
			values.add(total);
			values.add(percentage);

			totalsPerType.put(type, values);
		}

		return totalsPerType;
	}

	

	

	
	@Override
	/**
	 * Uses the fetchers to get the actual price depending the investments type.
	 * @return the actual value in usd for the Investment
	 */
	public double fetchInvestmentPriceInUsd(Investment investment) {

		double valueInUsd = investment.getActualPrice();

		// Crypto converter for non-stable-coins
		if (investment.getCategory().equalsIgnoreCase("crypto") 
				&& !investment.getType().equalsIgnoreCase("stable-coin")) {	
				valueInUsd = fetchCryptoValueInUsd(investment);
			}

		// fiat converter to usd
		if (investment.getCategory().equalsIgnoreCase("fiat") 
				&& investment.getType().equalsIgnoreCase("cash")) {

			// convert ARS using the blue rate
			if (investment.getCurrency().equalsIgnoreCase("ars")) {
				valueInUsd = fetchArsToBlueRate(investment);
			}

			// convert any other currency (except dollars and ars) to USD
			if (!investment.getCurrency().equalsIgnoreCase("usd")
					&& !investment.getCurrency().equalsIgnoreCase("ars")) {
				valueInUsd = fetchForexValueInUsd(investment);
			}
		}
		return valueInUsd;
	}

	
	private double fetchCryptoValueInUsd(Investment investment) {
		
		try {
			double cryptoPrice = cryptoPriceFetcher.getActualValueInUSD(investment.getName());
			return cryptoPrice;
			
		} catch (Exception e) {
			logger.error("Error fetching actual price for token: {}", investment.getToken(), e);
            return investment.getActualPrice(); 
		}
	}
	
	private double fetchArsToBlueRate(Investment investment) {
		
		try {
			double blueRate = blueRateFetcher.getBlueRate();
			return blueRate;
		} catch (Exception e) {
	        logger.error("Error fetching actual price for ARS: {}", investment.getToken(), e);
	        return investment.getActualPrice(); 
		}
	}
	
	private double fetchForexValueInUsd(Investment investment) {
		
		try {
			double exchangeRate = forexRateFetcher.getUsdRate(investment.getCurrency());
			return exchangeRate;
		
		} catch (Exception e) {
	        logger.error("Error fetching actual price for ARS: {}", investment.getToken(), e);
	        return investment.getActualPrice(); 	
		}
	}
	
	

	private double formatDoubleWithTwoDecimals(double number) {
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		return Double.parseDouble(decimalFormat.format(number));
	}
	
	
}
