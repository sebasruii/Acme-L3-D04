
package acme.forms;

import java.util.Map;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractForm;

public class AdministratorDashboard extends AbstractForm {

	protected static final long		serialVersionUID	= 1L;

	private Map<String, Integer>	totalUsers;

	private Double					peepsRatio;

	private Double					bulletinsRatio;

	private Map<Money, Statistics>	offerBudgetByCurrency;

	private Statistics				notesInLast10Weeks;

}
