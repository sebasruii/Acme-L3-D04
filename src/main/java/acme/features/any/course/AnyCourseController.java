
package acme.features.any.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.courses.Course;
import acme.features.authenticated.practicum.AuthenticatedPracticumListService;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyCourseController extends AbstractController<Any, Course> {

	@Autowired
	AnyCourseListService						listService;

	@Autowired
	AnyCourseShowService						showService;

	@Autowired
	protected AuthenticatedPracticumListService	listPracticumService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
