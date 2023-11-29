package perlas.de.portfolio.service;

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
			if (investment.getPrice() != 0.0) {
				investmentToUpdate.setPrice(investment.getPrice());
			}
			if (investment.getPurchasedDate() != null) {
				investmentToUpdate.setPurchasedDate(investment.getPurchasedDate());
			}
			if (investment.getQty() != 0.0) {
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
	public double getTotalValueInUsdByCategory(String category) {

		double totalUsdValue = 0.0;
		List<Investment> investmentsByCategory = this.listInvestmentByCategory(category);

		for (Investment investment : investmentsByCategory) {
			totalUsdValue += this.getInvestmentValueInUsd(investment);
		}
	

		return totalUsdValue;
	}

	@Override
	public double getTotalValueInUsdByType(String type) {

		double totalUsdValue = 0.0;
		List<Investment> investmentsByType = this.listInvestmentByType(type);

		for (Investment investment : investmentsByType) {
			totalUsdValue += this.getInvestmentValueInUsd(investment);
		}

		return totalUsdValue;
	}

	@Override
	public double getInvestmentValueInUsd(Investment investment) {

		double valueInUsd = investment.getPrice() * investment.getQty();

		// Crypto converter for non-stable-coins
		if (investment.getCategory().equalsIgnoreCase("crypto")) {
			if (!investment.getType().equalsIgnoreCase("stable-coin")) {
				double actualPrice = -1;
				try {
					actualPrice = cryptoPriceFetcher.getActualValueInUSD(investment.getName());
					valueInUsd = investment.getQty() * actualPrice;
					
				} catch (Exception e) {
					logger.error("Error fetching actual price for token: {}", investment.getToken(), e);
	                
				}
			}
		}

		// fiat converter to usd
		if (investment.getCategory().equalsIgnoreCase("fiat") && investment.getType().equalsIgnoreCase("cash")) {

			// convert ARS using the blue rate
			if (investment.getCurrency().equalsIgnoreCase("ars")) {
				double blueRate = blueRateFetcher.getBlueRate();
				valueInUsd /= blueRate;
			}

			// convert any other currency (except dollars and ars) to USD
			if (!investment.getCurrency().equalsIgnoreCase("usd")
					&& !investment.getCurrency().equalsIgnoreCase("ars")) {
				double exchangeRate = forexRateFetcher.getUsdRate(investment.getCurrency());
				valueInUsd /= exchangeRate;
			}
		}

		return valueInUsd;
	}

	@Override
	public Map<String, Double> getTotalsPerCategory() {

		Map<String, Double> totalsPerCategory = new HashMap<>();
		Set<String> categories = this.getAllInvestmentCategories();

		for (String category : categories) {
			double total = this.getTotalValueInUsdByCategory(category);

			totalsPerCategory.put(category, total);
		}

		return totalsPerCategory;
	}

	@Override
	public Map<String, Double> getTotalsPerType() {

		Map<String, Double> totalsPerType = new HashMap<>();
		Set<String> types = this.getAllInvestmentTypes();

		for (String type : types) {
			double total = this.getTotalValueInUsdByType(type);

			totalsPerType.put(type, total);
		}

		return totalsPerType;
	}

}
