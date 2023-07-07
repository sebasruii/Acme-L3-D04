
package acme.features.assistant.dashboard;

import org.springframework.stereotype.Service;

import acme.forms.AssistantDashboard;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {
	// Internal state ---------------------------------------------------------

	//	@Autowired
	//	protected AssistantDashboardRepository repository;
	//
	//	// AbstractService interface ----------------------------------------------
	//
	//
	//	@Override
	//	public void check() {
	//		super.getResponse().setChecked(true);
	//	}
	//
	//	@Override
	//	public void authorise() {
	//		super.getResponse().setAuthorised(true);
	//	}
	//
	//	@Override
	//	public void load() {
	//		int assistantId;
	//		AssistantDashboard dashboard;
	//		final Map<NatureType, Integer> tutorialCount;
	//		Statistics sessionTimeStatistics;
	//		Statistics tutorialTimeStatistics;
	//
	//		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
	//
	//		//		tutorialCount = this.repository.numberOfTutorialsByNature();
	//
	//		sessionTimeStatistics = new Statistics();
	//		sessionTimeStatistics.setCount(this.repository.countOfSessionsByAssistantId(assistantId));
	//		sessionTimeStatistics.setAverage(this.repository.averageTimeOfSessionsByAssistantId(assistantId));
	//		sessionTimeStatistics.setStDev(this.repository.deviationTimeOfSessionsByAssistantId(assistantId, sessionTimeStatistics.getAverageValue(), sessionTimeStatistics.getCount()));
	//		sessionTimeStatistics.setMinimum(this.repository.minimumTimeOfSessionsByAssistantId(assistantId));
	//		sessionTimeStatistics.setMaximum(this.repository.maximumTimeOfSessionsByAssistantId(assistantId));
	//
	//		tutorialTimeStatistics = new Statistics();
	//		tutorialTimeStatistics.setAverage(0.);
	//		tutorialTimeStatistics.setStDev(0.);
	//		tutorialTimeStatistics.setMaximum(0.);
	//		tutorialTimeStatistics.setMinimum(0.);
	//		tutorialTimeStatistics.setCount(this.repository.countOfTutorialsByAssistantId(assistantId));
	//		tutorialTimeStatistics.setAverage(this.repository.averageTimeOfTutorialsByAssistantId(assistantId));
	//		tutorialTimeStatistics.setDeviationValue(this.repository.deviationTimeOfTutorialsByAssistantId(assistantId, tutorialTimeStatistics.getAverageValue(), tutorialTimeStatistics.getCount()));
	//		tutorialTimeStatistics.setMinimum(this.repository.minimumTimeOfTutorialsByAssistantId(assistantId));
	//		tutorialTimeStatistics.setMaximum(this.repository.maximumTimeOfTutorialsByAssistantId(assistantId));
	//
	//		dashboard = new AssistantDashboard();
	//
	//		dashboard.setTutorialCount(tutorialCount);
	//		dashboard.setSessionTimeStatistics(sessionTimeStatistics);
	//		dashboard.setTutorialTimeStatistics(tutorialTimeStatistics);
	//
	//		super.getBuffer().setData(dashboard);
	//	}
	//
	//	@Override
	//	public void unbind(final AssistantDashboard object) {
	//		Tuple tuple;
	//
	//		tuple = super.unbind(object, "tutorialCount", "sessionTimeStatistics", "tutorialTimeStatistics");
	//
	//		super.getResponse().setData(tuple);
	//	}
}
