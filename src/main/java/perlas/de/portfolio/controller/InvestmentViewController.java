package perlas.de.portfolio.controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import perlas.de.portfolio.entities.Investment;
import perlas.de.portfolio.service.InvestmentService;

/*
 * Webapp controller
 */

@Controller
@RequestMapping("/investments")
public class InvestmentViewController {
	

	@Autowired
	private InvestmentService investmentService;


	//					CREATE

	@GetMapping("/new")
	public String newInvestmentForm(Model model) {
		Investment investment = new Investment();
		model.addAttribute("investment", investment);
		
		return "new_investment"; // .html
	}
	
	
	@PostMapping("/")
	public String saveInvestment(@ModelAttribute("investment") Investment investment) {
		
		// Set actualPrice to purchasedPrice before saving
	    investment.setActualPrice(investment.getPrice());
	    investment.setActualValueInUSD(1.0);             // DEFAULT
	    
		if (investment.getPurchasedDate() == null) {
			investment.setPurchasedDate(LocalDate.now());
		}
		
		investmentService.saveInvestment(investment);

		return "redirect:/investments/"; // save the investment and go back to the list (investment.html template)
	}
	
	
	
	//					UPDATE
	
	@PostMapping("/{id}")
	public String updateInvestment(@PathVariable int id, 
			@ModelAttribute("investment") Investment investment, 
			Model model) {
		
		Investment investmentToUpdate = investmentService.getInvestmentById(id);
		
		investmentToUpdate.setName(investment.getName());
		investmentToUpdate.setPrice(investment.getPrice());
		investmentToUpdate.setActualPrice(investment.getActualPrice());
		investmentToUpdate.setActualValueInUSD(investment.getActualValueInUSD());
		investmentToUpdate.setQty(investment.getQty());
		investmentToUpdate.setToken(investment.getToken());
		investmentToUpdate.setCategory(investment.getCategory());
		investmentToUpdate.setType(investment.getType());
		investmentToUpdate.setCurrency(investment.getCurrency());
		
		// if no date selected on the form, keep with the original purchased date
		if (investment.getPurchasedDate() != null) {
			investmentToUpdate.setPurchasedDate(investment.getPurchasedDate());			
		}
			
		investmentService.updateInvestment(investmentToUpdate);  // save
		
		return "redirect:/investments/";                         // redirect
	}
	
	
	@GetMapping("/edit/{id}")
	public String updateInvestmentForm(@PathVariable int id, Model model) {
		model.addAttribute("investment", investmentService.getInvestmentById(id));
		return "edit_investment";
	}
	
	
	//					DELETE
	
	@GetMapping("/del/{id}")
	public String deleteInvestment(@PathVariable int id) {
		investmentService.deleteInvestment(id);
		
		return "redirect:/investments/";
	}
	
	
	//					READ
	
	
	@GetMapping("by_id/{id}")
	public String getInvestmentById(@PathVariable int id, Model model) {
		String title = "#" + id + " Investments";
		model.addAttribute(title, "title");
		
		
		model.addAttribute("investments", investmentService.getInvestmentById(id));
		model.addAttribute("id", id);
		
		return "filtered_investments";
	}
	
	
	@GetMapping("/")
	public String listAllInvestments(Model model) {
		
		model.addAttribute("investments", investmentService.getAllInvestments());
		
		return "investments"; // returns investments.html
	}
	

	
	@GetMapping("/by_type/{type}")
	public String listInvestmentsByType(@PathVariable String type, Model model) {
		List<Investment> investments = investmentService.listInvestmentByType(type);
		
		String title = type + " Investments";
		model.addAttribute("title", title);
		
		model.addAttribute("investments", investments);
		model.addAttribute("type", type);
		
		return "filtered_investments";
	}
	
	
	@GetMapping("/by_category/{category}")
	public String listInvestmentsByCategory(@PathVariable String category, Model model ) {
		
		List<Investment> investments = investmentService.listInvestmentByCategory(category);
		
		String title = category + " Investments";
		model.addAttribute("title", title);
		
		model.addAttribute("investments", investments);
		model.addAttribute("category", category);
		
		return "filtered_investments";
	}
	
	@GetMapping("/by_currency/{currency}")
	public String listInvestmentByCurrency (@PathVariable String currency, Model model) {
		
		String title = currency + " Investments";
		model.addAttribute("title", title);
		
		List<Investment> investments = investmentService.listInvestentByCurrency(currency);
		model.addAttribute("investments", investments);
		model.addAttribute("currency",currency);
		
		return "filtered_investments";
	}
	
	@GetMapping("/by_token/{token}")
	public String listInvestmentsByToken(@PathVariable String token, Model model) {
		
		List<Investment> investments = investmentService.listInvestmentByToken(token);
		
		String title = token + " Investments";
		model.addAttribute("title", title);
		
		model.addAttribute("investments", investments);
		model.addAttribute("token", token);
		
		return "filtered_investments";
	}
	
	
	@GetMapping("/summary")
	public String portfolioSummary(Model model) {
		
		Set<String> investmentTypes = investmentService.getAllInvestmentTypes();
		Set<String> investmentCategories = investmentService.getAllInvestmentCategories();
		Map<String, List<Double>> typeTotals = investmentService.getTotalsPerType();
		Map<String, List<Double>> categoryTotals = investmentService.getTotalsPerCategory();
		double totalPortfolioValue = investmentService.getPortfolioValueInUsd();
		
		model.addAttribute("investmentTypes", investmentTypes);
		model.addAttribute("investmentCategories", investmentCategories);
		model.addAttribute("typeTotals", typeTotals);
		model.addAttribute("categoryTotals", categoryTotals);
		model.addAttribute("totalPortfolioValue", totalPortfolioValue);
		
		return "investment-summary";
		
	}

}
