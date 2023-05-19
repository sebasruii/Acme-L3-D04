
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;

public interface LecturerLectureRepositoryTest extends AbstractRepository {

	@Query("select l from Lecture l where l.lecturer.userAccount.username = :lecturer")
	Collection<Lecture> findLecturesByLecturer(String lecturer);
}
