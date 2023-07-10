
package acme.testing.lecturer.lectureCourse;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureCourse;
import acme.framework.repositories.AbstractRepository;

public interface LecturerLectureCourseRepositoryTest extends AbstractRepository {

	@Query("select l from Lecture l where l.lecturer.userAccount.username = :lecturer")
	Collection<Lecture> findLecturesByLecturer(String lecturer);

	@Query("select c from Course c where c.lecturer.userAccount.username = :lecturer")
	Collection<Course> findCoursesByLecturer(String lecturer);

	@Query("select lc from LectureCourse lc where lc.lecture.lecturer.userAccount.username = :lecturer")
	Collection<LectureCourse> findLectureCoursesByLecturer(String lecturer);
}
