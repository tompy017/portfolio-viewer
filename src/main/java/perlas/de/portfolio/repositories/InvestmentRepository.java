package perlas.de.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import perlas.de.portfolio.entities.Investment;

public interface InvestmentRepository extends JpaRepository<Investment, Integer> {
	

}
