package perlas.de.portfolio.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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



@Controller
@RequestMapping("/investments")
public class InvestmentViewController {
	

	@Autowired
	private InvestmentService investmentService;

	@GetMapping("/")
	public String listAllInvestments(Model model) {
		model.addAttribute("investments", investmentService.getAllInvestments());
		return "investments"; // returns investments.html
	}

	@GetMapping("/new")
	public String newInvestmentForm(Model model) {
		Investment investment = new Investment();
		model.addAttribute("investment", investment);

		return "new_investment"; // .html
	}

	@PostMapping("/")
	public String saveInvestment(@ModelAttribute("investment") Investment investment) {
		investmentService.saveInvestment(investment);

		return "redirect:/investments/"; // save the investment and goes back to the list
	}

	@GetMapping("/edit/{id}")
	public String updateInvestmentForm(@PathVariable int id, Model model) {
		model.addAttribute("investment", investmentService.getInvestmentById(id));
		return "edit_investment";
	}

	@PostMapping("/{id}")
	public String updateInvestment(@PathVariable int id, 
			@ModelAttribute("investment") Investment investment, 
			Model model) {

		Investment investmentToUpdate = investmentService.getInvestmentById(id);

		investmentToUpdate.setName(investment.getName());
		investmentToUpdate.setPrice(investment.getPrice());		
		investmentToUpdate.setQty(investment.getQty());
		investmentToUpdate.setToken(investment.getToken());
		investmentToUpdate.setType(investment.getType());
		// if no date selected on the form, keep with the original purchased date
		if (investment.getPurchasedDate() != null) {
			investmentToUpdate.setPurchasedDate(investment.getPurchasedDate());			
		}
		
		
		investmentService.updateInvestment(investmentToUpdate);  // save

		return "redirect:/investments/";                         // redirect
	}

	
	@GetMapping("/del/{id}")
	public String deleteInvestment(@PathVariable int id) {
		investmentService.deleteInvestment(id);

		return "redirect:/investments/";
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
	
	
	@GetMapping("/by_token/{token}")
	public String listInvestmentsByToken(@PathVariable String token, Model model) {
		
		List<Investment> investments = investmentService.listInvestmentByToken(token);
		
		String title = token + " Investments";
		model.addAttribute("title", title);
		
		model.addAttribute("investments", investments);
		model.addAttribute("token", token);
		
		return "filtered_investments";
	}

}
