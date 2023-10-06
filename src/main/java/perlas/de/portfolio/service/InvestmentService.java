package perlas.de.portfolio.service;

import java.util.List;

import perlas.de.portfolio.entities.Investment;

public interface InvestmentService {
	
	public Investment saveInvestment(Investment investment);
	
	public List<Investment> getAllInvestments();
	
	public Investment getInvestmentById(int id);
	
	public Investment updateInvestment(Investment investment);
	
	public String deleteInvestment(int id);
	
	

}
