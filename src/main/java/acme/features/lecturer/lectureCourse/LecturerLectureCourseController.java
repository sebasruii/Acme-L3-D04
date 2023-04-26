
package acme.features.lecturer.lectureCourse;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.lectures.LectureCourse;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureCourseController extends AbstractController<Lecturer, LectureCourse> {

	@Autowired
	protected LecturerLectureCourseAddService		addService;

	@Autowired
	protected LecturerLectureCourseDeleteService	deleteService;

	@Autowired
	protected LecturerLectureCourseListService		listService;

	@Autowired
	LecturerLectureCourseShowService				showService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("add", "create", this.addService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
