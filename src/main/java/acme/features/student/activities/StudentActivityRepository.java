
package acme.features.student.activities;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.enrolment.Enrolment;
import acme.entities.workbook.Activity;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("SELECT a FROM Activity a")
	Collection<Activity> findAllActivities();

	@Query("SELECT a FROM Activity a WHERE a.id = :id")
	Activity findActivityById(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.id = :id")
	Collection<Activity> findAllActivitiesByEnrolment(int id);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findCourseById(int id);

}
