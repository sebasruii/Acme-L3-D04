
package acme.features.lecturer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select count(l) from Lecture l where l.lecturer.id = :lecturerId and l.lectureType = acme.entities.NatureType.NatureType.THEORY")
	Integer totalNumberOfTheoryLectures(int lecturerId);

	@Query("select count(l) from Lecture l where l.lecturer.id = :lecturerId and l.lectureType = acme.entities.NatureType.NatureType.HANDS_ON")
	Integer totalNumberOfHandsOnLectures(int lecturerId);

	@Query("select avg(l.estimatedLearningTime ) from Lecture l where l.lecturer.id = :lecturerId")
	Double averageLearningTimeOfLectures(int lecturerId);

	@Query("select max(l.estimatedLearningTime ) from Lecture l where l.lecturer.id = :lecturerId")
	Double maxLearningTimeOfLectures(int lecturerId);

	@Query("select min(l.estimatedLearningTime ) from Lecture l where l.lecturer.id = :lecturerId")
	Double minLearningTimeOfLectures(int lecturerId);

	@Query("select stddev(l.estimatedLearningTime) from Lecture l where l.lecturer.id = :lecturerId")
	Double stdevLearningTimeOfLectures(int lecturerId);

	@Query("select avg(select sum(lc.lecture.estimatedLearningTime) from LectureCourse lc where lc.course.id = c.id) from Course c where c.lecturer.id = :lecturerId")
	Double averageLearningTimeOfCourses(int lecturerId);

	@Query("select max(select sum(lc.lecture.estimatedLearningTime) from LectureCourse lc where lc.course.id = c.id) from Course c where c.lecturer.id = :lecturerId")
	Double maxLearningTimeOfCourses(int lecturerId);

	@Query("select min(select sum(lc.lecture.estimatedLearningTime) from LectureCourse lc where lc.course.id = c.id) from Course c where c.lecturer.id = :lecturerId")
	Double minLearningTimeOfCourses(int lecturerId);

	@Query("select stddev((select sum(lc.lecture.estimatedLearningTime) from LectureCourse lc where lc.course.id = c.id)) from Course c where c.lecturer.id = :lecturerId")
	Double stdevLearningTimeOfCourses(int lecturerId);

}
