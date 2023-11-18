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

import perlas.de.portfolio.entities.Investment;
import perlas.de.portfolio.repositories.InvestmentRepository;

@Service
public class InvestmentServiceImpl implements InvestmentService {

	@Autowired
	private InvestmentRepository investmentRepository;

	
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
		List<Investment> investmentsByType = investmentRepository
				.findAll()
				.stream()
				.filter(investment -> investment.getType().equalsIgnoreCase(type))
				.collect(Collectors.toList());

		return investmentsByType;
	}


	@Override
	public List<Investment> listInvestmentByCategory(String category) {
		List<Investment> investmentByCategory = investmentRepository
				.findAll()
				.stream()
				.filter(investment -> investment.getCategory().equalsIgnoreCase(category))
				.collect(Collectors.toList());
		return investmentByCategory;
	}


	@Override
	public List<Investment> listInvestmentByToken(String token) {
		List<Investment> investmentByToken = investmentRepository
				.findAll()
				.stream()
				.filter(investment -> investment.getToken().equalsIgnoreCase(token))
				.collect(Collectors.toList());
		
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
		
		//get unique category types
		for (Investment investment : investmentRepository.findAll()) {
			categories.add(investment.getCategory());
		}
		
		return categories;
	}


	

	
}
