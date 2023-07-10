
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findPracticumSessionsByPracticumId(int id);

	@Query("select ps.practicum from PracticumSession ps where ps.id = :id")
	Practicum findPracticumByPracticumSessionId(int id);

	@Query("select ps from PracticumSession ps where ps.id = :id")
	PracticumSession findPracticumSessionById(int id);

	@Query("select c from Company c where c.id = :id")
	Company findCompanyById(int id);

}
