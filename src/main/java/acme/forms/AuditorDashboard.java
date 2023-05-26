
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Double						totalNumberOfAudits;
	Double						averageNumberOfAuditingRecords;
	Double						deviationOfAuditingRecords;
	Double						minimumNumberOfAuditingRecords;
	Double						maximumNumberOfAuditingRecords;
	Double						averageTimeOfAuditingRecords;
	Double						timeDeviationOfAuditingRecords;
	Double						minimumTimeOfAuditingRecords;
	Double						maximumTimeOfAuditingRecords;
}
