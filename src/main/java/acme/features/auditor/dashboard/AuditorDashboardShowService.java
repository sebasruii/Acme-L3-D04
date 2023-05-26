
package acme.features.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AuditorDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	@Autowired
	protected AuditorDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id;
		id = super.getRequest().getPrincipal().getActiveRoleId();

		AuditorDashboard dashboard;
		Double totalNumberOfAudits;
		Double averageNumberOfAuditingRecords;
		Double deviationOfAuditingRecords;
		Double minimumNumberOfAuditingRecords;
		Double maximumNumberOfAuditingRecords;
		Double averageTimeOfAuditingRecords;
		Double timeDeviationOfAuditingRecords;
		Double minimumTimeOfAuditingRecords;
		Double maximumTimeOfAuditingRecords;

		totalNumberOfAudits = this.repository.totalNumberOfAudits(id);
		averageNumberOfAuditingRecords = this.repository.averageNumberOfAuditingRecords(id);
		minimumNumberOfAuditingRecords = this.repository.minimumNumberOfAuditingRecords(id);
		maximumNumberOfAuditingRecords = this.repository.maximumNumberOfAuditingRecords(id);
		deviationOfAuditingRecords = this.repository.deviationOfAuditingRecords(id);
		averageTimeOfAuditingRecords = this.repository.averageTimeOfAuditingRecords(id);
		timeDeviationOfAuditingRecords = this.repository.timeDeviationOfAuditingRecords(id);
		minimumTimeOfAuditingRecords = this.repository.minimumTimeOfAuditingRecords(id);
		maximumTimeOfAuditingRecords = this.repository.maximumTimeOfAuditingRecords(id);

		dashboard = new AuditorDashboard();
		dashboard.setTotalNumberOfAudits(totalNumberOfAudits);
		dashboard.setAverageNumberOfAuditingRecords(averageNumberOfAuditingRecords);
		dashboard.setMinimumNumberOfAuditingRecords(minimumNumberOfAuditingRecords);
		dashboard.setMaximumNumberOfAuditingRecords(maximumNumberOfAuditingRecords);
		dashboard.setDeviationOfAuditingRecords(deviationOfAuditingRecords);
		dashboard.setAverageTimeOfAuditingRecords(averageTimeOfAuditingRecords);
		dashboard.setTimeDeviationOfAuditingRecords(timeDeviationOfAuditingRecords);
		dashboard.setMinimumTimeOfAuditingRecords(minimumTimeOfAuditingRecords);
		dashboard.setMaximumTimeOfAuditingRecords(maximumTimeOfAuditingRecords);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumberOfAudits", "averageNumberOfAuditingRecords", "minimumNumberOfAuditingRecords", "maximumNumberOfAuditingRecords", "deviationOfAuditingRecords", "averageTimeOfAuditingRecords",
			"timeDeviationOfAuditingRecords", "minimumTimeOfAuditingRecords", "maximumTimeOfAuditingRecords");

		super.getResponse().setData(tuple);
	}
}
