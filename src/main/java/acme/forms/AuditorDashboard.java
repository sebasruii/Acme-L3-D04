
package acme.forms;

import acme.framework.data.AbstractForm;

public class AuditorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	private int					totalNumAudits;

	// Average, deviation, minimum, and maximum number of auditing records in audits.
	private Statistics			audits;

	// Average, deviation, minimum, and maximum time of auditing records.
	private Statistics			auditRecs;
}
