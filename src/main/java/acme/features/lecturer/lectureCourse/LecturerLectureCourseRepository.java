
package acme.features.lecturer.lectureCourse;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureCourse;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("select l from Lecture l where l.lecturer.id = :lecturerId")
	Collection<Lecture> findLecturesByLecturerId(int lecturerId);

	@Query("select l from Lecture l where l.id = :lectureId")
	Lecture findOneLectureById(int lectureId);

	@Query("select lc from LectureCourse lc where lc.course.id = :courseId and lc.lecture.id = :lectureId")
	LectureCourse findOneLectureCourseBy(int courseId, int lectureId);

	@Query("select lc.course from LectureCourse lc where lc.lecture.id = :lectureId")
	Collection<Course> findCoursesByLectureId(int lectureId);

	@Query("select lc from LectureCourse lc where lc.course.id = :courseId")
	Collection<LectureCourse> findLectureCoursesByCourseId(int courseId);

	@Query("select lc from LectureCourse lc where lc.id = :lectureCourseId")
	LectureCourse findOneLectureCourseById(int lectureCourseId);
}
