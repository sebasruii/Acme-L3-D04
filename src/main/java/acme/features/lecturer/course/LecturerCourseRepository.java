
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureCourse;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findCoursesByLecturerId(int lecturerId);

	@Query("select c from Course c where c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("select l from Lecturer l where l.id = :lecturerId")
	Lecturer findOneLecturerById(int lecturerId);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select lc from LectureCourse lc where lc.course.id = :courseId")
	Collection<LectureCourse> findManyLectureCoursesByCourseId(int courseId);

	@Query("select count(lc) from LectureCourse lc where lc.course.id = :courseId and lc.lecture.lectureType = acme.entities.NatureType.NatureType.THEORY")
	Integer numberOfTheoryLecturesPerCourse(int courseId);

	@Query("select count(lc) from LectureCourse lc where lc.course.id = :courseId and lc.lecture.lectureType = acme.entities.NatureType.NatureType.HANDS_ON")
	Integer numberOfHandsOnLecturesPerCourse(int courseId);

	@Query("select lc.lecture from LectureCourse lc where lc.course.id = :courseId")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);


	@Query("select c from Configuration c")
	Configuration findSystemConfiguration();


}
