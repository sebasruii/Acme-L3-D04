
package acme.features.auditor.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.AuditingRecord;
import acme.forms.AuditorDashboard;
import acme.forms.Statistics;
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

		final Integer totalNumberOfTheoryAudits;
		final Integer totalNumberOfHandsOnAudits;
		final Statistics auditingRecordStatistics;
		Double averageNumberOfAuditingRecords;
		Double deviationOfAuditingRecords;
		Double minimumNumberOfAuditingRecords;
		Double maximumNumberOfAuditingRecords;

		final Statistics timeStatistics;
		Double averageTimeOfAuditingRecords;
		Double timeDeviationOfAuditingRecords;
		Double minimumTimeOfAuditingRecords;
		Double maximumTimeOfAuditingRecords;

		totalNumberOfTheoryAudits = this.repository.totalNumberOfTheoryAudits(id);
		totalNumberOfHandsOnAudits = this.repository.totalNumberOfHandsOnAudits(id);
		averageNumberOfAuditingRecords = this.repository.averageNumberOfAuditingRecords(id);
		minimumNumberOfAuditingRecords = this.repository.minimumNumberOfAuditingRecords(id);
		maximumNumberOfAuditingRecords = this.repository.maximumNumberOfAuditingRecords(id);
		deviationOfAuditingRecords = this.repository.deviationOfAuditingRecords(id);
		averageTimeOfAuditingRecords = this.repository.averageTimeOfAuditingRecords(id);
		timeDeviationOfAuditingRecords = this.repository.timeDeviationOfAuditingRecords(id);
		minimumTimeOfAuditingRecords = this.repository.minimumTimeOfAuditingRecords(id);
		maximumTimeOfAuditingRecords = this.repository.maximumTimeOfAuditingRecords(id);

		auditingRecordStatistics = new Statistics();
		auditingRecordStatistics.setCount(null);
		auditingRecordStatistics.setAverage(averageNumberOfAuditingRecords);
		if (deviationOfAuditingRecords.isNaN())
			auditingRecordStatistics.setStDev(null);
		else
			auditingRecordStatistics.setStDev(deviationOfAuditingRecords);
		auditingRecordStatistics.setMinimum(minimumNumberOfAuditingRecords);
		auditingRecordStatistics.setMaximum(maximumNumberOfAuditingRecords);

		timeStatistics = new Statistics();
		timeStatistics.setCount(null);
		timeStatistics.setAverage(averageTimeOfAuditingRecords);
		if (timeDeviationOfAuditingRecords.isNaN())
			timeStatistics.setStDev(null);
		else
			timeStatistics.setStDev(timeDeviationOfAuditingRecords);

		timeStatistics.setMinimum(minimumTimeOfAuditingRecords);
		timeStatistics.setMaximum(maximumTimeOfAuditingRecords);

		dashboard = new AuditorDashboard();
		dashboard.setTotalNumberOfTheoryAudits(totalNumberOfTheoryAudits);
		dashboard.setTotalNumberOfHandsOnAudits(totalNumberOfHandsOnAudits);
		final List<AuditingRecord> auditingRecords = this.repository.findAllAuditingRecordsByAuditorId(id);
		if (auditingRecords.isEmpty()) {
			dashboard.setAuditingRecordStatistics(null);
			dashboard.setTimeStatistics(null);
		} else {
			dashboard.setAuditingRecordStatistics(auditingRecordStatistics);
			dashboard.setTimeStatistics(timeStatistics);
		}

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumberOfTheoryAudits", "totalNumberOfHandsOnAudits", "auditingRecordStatistics", "timeStatistics");

		super.getResponse().setData(tuple);
	}
}
