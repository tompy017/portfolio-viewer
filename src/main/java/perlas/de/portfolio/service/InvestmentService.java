package perlas.de.portfolio.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import perlas.de.portfolio.entities.Investment;

public interface InvestmentService {
	
	public Investment saveInvestment(Investment investment);
	
	public List<Investment> getAllInvestments();
	
	public Investment getInvestmentById(int id);
	
	public Investment updateInvestment(Investment investment);
	
	public String deleteInvestment(int id);
	
	public List<Investment> listInvestmentByType(String type);
	
	public List<Investment> listInvestmentByCategory(String category);
	
	public List<Investment> listInvestmentByToken(String token);
	
	public Set<String> getAllInvestmentTypes();
	
	public Set<String> getAllInvestmentCategories();
	

}
