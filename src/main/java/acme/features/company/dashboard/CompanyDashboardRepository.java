
package acme.features.company.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findOneCompanyByUserAccountId(int userAccountId);

	//Practicum	
	@Query("select avg((select sum(datediff(sp.finishDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)) from Practicum p where p.company.id = ?1")
	double findAveragePracticumLength(int companyId);

	@Query("select stddev((select sum(datediff(sp.finishDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)) from Practicum p where p.company.id = ?1")
	double findDeviationPracticumLength(int companyId);

	@Query("select min((select sum(datediff(sp.finishDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)) from Practicum p where p.company.id = ?1")
	double findMinimumPracticumLength(int companyId);

	@Query("select max((select sum(datediff(sp.finishDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)) from Practicum p where p.company.id = ?1")
	double findMaximumPracticumLength(int companyId);

	@Query("select count(p) from Practicum p where p.company.id = ?1")
	int findCountPracticum(int companyId);

	@Query("SELECT FUNCTION('MONTH', sp.startDate), COUNT(sp) FROM PracticumSession sp WHERE sp.practicum.company.id = ?1 GROUP BY FUNCTION('MONTH', sp.startDate) ORDER BY COUNT(sp) DESC")
	List<Object[]> findNumPracticumPerMonth(int companyId);

	//PracticumSession
	@Query("select count(sp) from PracticumSession sp where sp.practicum.company.id = ?1")
	int findCountSession(int companyId);

	@Query("select avg(datediff(sp.finishDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1")
	double findAverageSessionLength(int companyId);

	@Query("select stddev(datediff(sp.finishDate,sp.startDate)) from PracticumSession  sp where sp.practicum.company.id = ?1")
	double findDeviationSessionLength(int companyId);

	@Query("select min(datediff(sp.finishDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1")
	double findMinimumSessionLength(int companyId);

	@Query("select max(datediff(sp.finishDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1")
	double findMaximumSessionLength(int companyId);

}
