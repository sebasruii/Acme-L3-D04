
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyDashboard extends AbstractForm {
	// Serialisation identifier ---------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes ----------------------------------------------------------

	private Map<String, Long>	numPracticumPerMonth;
	private Statistics			practicumSessionStatistics;
	private Statistics			practicumStatistics;

	// Derived attributes --------------------------------------------------

	// Relationships -------------------------------------------------------

}
