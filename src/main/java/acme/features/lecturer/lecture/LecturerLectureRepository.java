
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureCourse;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("select l from Lecture l where l.lecturer.id = :lecturerId")
	Collection<Lecture> findLecturesByLecturerId(int lecturerId);

	@Query("select l from Lecturer l where l.id = :lecturerId")
	Lecturer findOneLecturerById(int lecturerId);

	@Query("select l from Lecture l where l.id = :lectureId")
	Lecture findOneLectureById(int lectureId);

	@Query("select lc from LectureCourse lc where lc.lecture.id = :lectureId")
	Collection<LectureCourse> findManyLectureCoursesByLectureId(int lectureId);

	@Query("select lc.lecture from LectureCourse lc where lc.course.id = :courseId")
	Collection<Lecture> findLecturesByCourseId(int courseId);

}
