package perlas.de.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perlas.de.portfolio.entities.Investment;
import perlas.de.portfolio.repositories.InvestmentRepository;

@Service
public class InvestmentServiceImpl implements InvestmentService {

	@Autowired
	private InvestmentRepository investmentRepository;

	public Investment saveInvestment(Investment investment) {

		return investmentRepository.save(investment); // this will be shown in postman
	}

	public List<Investment> getAllInvestments() {

		return investmentRepository.findAll();
	}

	public Investment getInvestmentById(int id) {

		return investmentRepository.findById(id).orElse(null);
	}

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

	public String deleteInvestment(int id) {

		if (investmentRepository.existsById(id)) {
			investmentRepository.deleteById(id);
			return "DELETED";

		} else {
			return "ID NOT EXISTS";
		}
	}

}
