
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.practicums.Practicum;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.draftMode = false")
	Collection<Practicum> findManyPracticum();

	@Query("select p from Practicum p where p.id = ?1")
	Practicum findOnePracticumById(int id);

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int userAccountId);

	@Query("select p from Practicum p where p.course.id =:id and p.draftMode = false")
	Collection<Practicum> findManyPracticumByCourseId(int id);

	@Query("select p from Practicum p where p.code = :code")
	Practicum findPracticumByCode(String code);

	@Query("select c from Course c where c.id= :id")
	Course findCourseById(int id);

}
