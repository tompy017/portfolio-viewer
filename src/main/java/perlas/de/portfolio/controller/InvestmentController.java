package perlas.de.portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import perlas.de.portfolio.entities.Investment;
import perlas.de.portfolio.service.InvestmentService;

@RestController
@RequestMapping("/api")
public class InvestmentController {
	private static final Logger logger = LoggerFactory.getLogger(InvestmentController.class);

	@Autowired
	private InvestmentService investmentService;

	@PostMapping("/addInvestment")
	public Investment addInvestment(@RequestBody Investment investment) {
		logger.info("JSON: {}", investment);
		return investmentService.saveInvestment(investment);
	}

	@PutMapping("/updateInvestment")
	public Investment updateInvestment(@RequestBody Investment investment) {
		return investmentService.updateInvestment(investment);
	}

	@GetMapping("/investmentById/{id}")
	public Investment findInvestmentById(@PathVariable int id) {
		return investmentService.getInvestmentById(id);
	}

	@GetMapping({ "/allInvestments", "/" })
	public List<Investment> listAllInvestments() {
		return investmentService.getAllInvestments();
	}

	@DeleteMapping("/deleteInvestment/{id}")
	public String deleteInvestmentById(@PathVariable int id) {

		return investmentService.deleteInvestment(id);
	}
}
