
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier ---------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes ----------------------------------------------------------

	private Map<String, Statistics>	mapStatisticsTutorials;
	private Statistics				tutorialsSessionsStatistics;

	// Derived attributes --------------------------------------------------

}
