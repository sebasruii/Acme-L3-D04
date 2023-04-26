
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.enrolment.Enrolment;
import acme.entities.workbook.Activity;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("SELECT e from Enrolment e")
	Collection<Enrolment> findAllEnrolments();

	@Query("SELECT e from Enrolment e where e.id = :enrolmentId")
	Enrolment findEnrolmentById(int enrolmentId);

	@Query("SELECT c from Course c")
	Collection<Course> findAllCourses();

	@Query("SELECT c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("SELECT s from Student s where s.id = :id")
	Student findStudentById(int id);

	@Query("SELECT c from Course c left join Enrolment e where e.id = :enrolmentId")
	Course findCourseByEnrolmentId(int enrolmentId);

	@Query("SELECT e from Enrolment e where e.student.id = :id")
	Collection<Enrolment> findEnrolmentsByStudentId(int id);

	@Query("SELECT a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findManyActivitiesByEnrolmentId(int id);

	@Query("SELECT c from Course c where c.draftMode = false")
	Collection<Course> findNotInDraftCourses();

	@Query("SELECT e.code from Enrolment e")
	Collection<String> findAllCodesFromEnrolments();

	@Query("SELECT e from Enrolment e where e.code = :code")
	Enrolment findAEnrolmentByCode(String code);

}
