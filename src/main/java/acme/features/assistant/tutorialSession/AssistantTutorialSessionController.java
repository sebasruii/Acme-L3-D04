
package acme.features.assistant.tutorialSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorialSessions.TutorialSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantTutorialSessionController extends AbstractController<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionListService		listService;

	@Autowired
	protected AssistantTutorialSessionShowService		showService;

	@Autowired
	protected AssistantTutorialSessionCreateService		createService;

	@Autowired
	protected AssistantTutorialSessionDeleteService		deleteService;

	@Autowired
	protected AssistantTutorialSessionUpdateService		updateService;

	@Autowired
	protected AssistantTutorialSessionPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("publish", "update", this.publishService);
	}
}
