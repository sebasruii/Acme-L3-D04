
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	private Integer				totalNumberOfTheoryAudits;
	private Integer				totalNumberOfHandsOnAudits;
	private Statistics			auditingRecordStatistics;
	private Statistics			timeStatistics;
}
