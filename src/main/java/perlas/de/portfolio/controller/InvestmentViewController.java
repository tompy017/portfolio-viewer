package perlas.de.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import perlas.de.portfolio.entities.Investment;
import perlas.de.portfolio.service.InvestmentService;

@Controller
public class InvestmentViewController {

	@Autowired
	private InvestmentService investmentService;

	@GetMapping({ "/investments", "/" })
	public String listAllInvestments(Model model) {
		model.addAttribute("investments", investmentService.getAllInvestments());
		return "investments"; // returns investments.html
	}

	@GetMapping("/investments/new")
	public String newInvestmentForm(Model model) {
		Investment investment = new Investment();
		model.addAttribute("investment", investment);

		return "new_investment"; // .html
	}

	@PostMapping("/investments")
	public String saveInvestment(@ModelAttribute("investment") Investment investment) {
		investmentService.saveInvestment(investment);

		return "redirect:/investments"; // save the investment and goes back to the list
	}

	@GetMapping("/investments/edit/{id}")
	public String updateInvestmentForm(@PathVariable int id, Model model) {
		model.addAttribute("investment", investmentService.getInvestmentById(id));
		return "edit_investment";
	}

	@PostMapping("/investments/{id}")
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

		return "redirect:/investments";                         // redirect
	}

	@GetMapping("/investments/del/{id}")
	public String deleteInvestment(@PathVariable int id) {
		investmentService.deleteInvestment(id);

		return "redirect:/investments";
	}

}
