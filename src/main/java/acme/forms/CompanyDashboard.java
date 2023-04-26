
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyDashboard extends AbstractForm {
	// Serialisation identifier ---------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes ----------------------------------------------------------

	private int					totalPracticums;
	private Statistics			practicumSessionStatistics;
	private Statistics			practicumStatistics;

	// Derived attributes --------------------------------------------------

	// Relationships -------------------------------------------------------

}
