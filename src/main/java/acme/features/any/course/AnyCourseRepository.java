
package acme.features.any.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findManyPublishedCourses();

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select count(lc) from LectureCourse lc where lc.course.id = :courseId and lc.lecture.lectureType = acme.entities.NatureType.NatureType.THEORY")
	int numberOfTheoryLecturesPerCourse(int courseId);

	@Query("select count(lc) from LectureCourse lc where lc.course.id = :courseId and lc.lecture.lectureType = acme.entities.NatureType.NatureType.HANDS_ON")
	int numberOfHandsOnLecturesPerCourse(int courseId);

}
