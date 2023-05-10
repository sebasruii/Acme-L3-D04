
package acme.features.company.dashboard;

import java.time.Month;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.CompanyDashboard;
import acme.forms.Statistics;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanyDashboardRepository repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Company company;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);

		status = company != null && principal.hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int companyId;
		CompanyDashboard companyDashboard;
		final Principal principal;
		int userAccountId;
		Company company;

		Integer countPracticum;

		Statistics practicumStatistics;
		Double averagePracticumLength;
		Double deviationPracticumLength;
		Double minimumPracticumLength;
		Double maximumPracticumLength;

		Integer countSession;

		Statistics practicumSessionStatistics;
		Double averageSessionLength;
		Double deviationSessionLength;
		Double minimumSessionLength;
		Double maximumSessionLength;

		final Map<String, Long> numPracticumPerMonth;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);
		companyId = company.getId();

		//Practicum
		countPracticum = this.repository.findCountPracticum(companyId);
		averagePracticumLength = this.repository.findAveragePracticumLength(companyId);
		deviationPracticumLength = this.repository.findDeviationPracticumLength(companyId);
		minimumPracticumLength = this.repository.findMinimumPracticumLength(companyId);
		maximumPracticumLength = this.repository.findMaximumPracticumLength(companyId);
		practicumStatistics = new Statistics();

		practicumStatistics.setCount(countPracticum);
		practicumStatistics.setAverage(averagePracticumLength);
		practicumStatistics.setStDev(deviationPracticumLength);
		practicumStatistics.setMinimum(minimumPracticumLength);
		practicumStatistics.setMaximum(maximumPracticumLength);

		numPracticumPerMonth = this.repository.findNumPracticumPerMonth(companyId).stream().collect(Collectors.toMap(key -> Month.of((int) key[0]).toString(), value -> (long) value[1]));

		//PracticumSession
		countSession = this.repository.findCountSession(companyId);
		averageSessionLength = this.repository.findAverageSessionLength(companyId);
		deviationSessionLength = this.repository.findDeviationSessionLength(companyId);
		minimumSessionLength = this.repository.findMinimumSessionLength(companyId);
		maximumSessionLength = this.repository.findMaximumSessionLength(companyId);

		practicumSessionStatistics = new Statistics();

		practicumSessionStatistics.setCount(countSession);
		practicumSessionStatistics.setAverage(averageSessionLength);
		practicumSessionStatistics.setStDev(deviationSessionLength);
		practicumSessionStatistics.setMinimum(minimumSessionLength);
		practicumSessionStatistics.setMaximum(maximumSessionLength);

		companyDashboard = new CompanyDashboard();

		companyDashboard.setNumPracticumPerMonth(numPracticumPerMonth);
		companyDashboard.setPracticumStatistics(practicumStatistics);
		companyDashboard.setPracticumSessionStatistics(practicumSessionStatistics);

		super.getBuffer().setData(companyDashboard);
	}

	@Override
	public void unbind(final CompanyDashboard companyDashboard) {
		Tuple tuple;

		tuple = super.unbind(companyDashboard, "numPracticumPerMonth", "practicumSessionStatistics", "practicumStatistics");

		super.getResponse().setData(tuple);
	}

}
