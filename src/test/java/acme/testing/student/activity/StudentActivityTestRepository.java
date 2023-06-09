
package acme.testing.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.enrolment.Enrolment;
import acme.entities.workbook.Activity;
import acme.framework.repositories.AbstractRepository;

public interface StudentActivityTestRepository extends AbstractRepository {

	@Query("SELECT a FROM Activity a WHERE a.enrolment.student.userAccount.username = :username")
	Collection<Activity> findAllActivitiesByStudentIdUsername(String username);

	@Query("SELECT e FROM Enrolment e WHERE e.student.userAccount.username = :username")
	Collection<Enrolment> findAllEnrolmentsByStudentUsername(String username);

}
