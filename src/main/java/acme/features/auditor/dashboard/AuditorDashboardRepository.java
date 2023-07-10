
package acme.features.auditor.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.NatureType.NatureType;
import acme.entities.audits.AuditingRecord;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.userAccount.id = :accountId")
	Auditor findAuditorByAccountId(@Param("accountId") int accountId);

	@Query("SELECT count(a) from Audit a WHERE a.auditor.id = :auditorId AND a.course.id = :courseId")
	Integer countAuditsAuditorByCourse(@Param("courseId") Integer courseId, @Param("auditorId") Integer auditorId);

	@Query("SELECT c FROM Course c")
	List<Course> findCourses();

	@Query("SELECT lc.lecture FROM LectureCourse lc WHERE lc.course.id = :id")
	List<Lecture> findAllLecturesByCourse(@Param("id") Integer id);

	default NatureType courseType(final List<Lecture> lecturesCourse) {
		int theory = 0;
		int handsOn = 0;
		NatureType res = NatureType.THEORY;
		for (final Lecture l : lecturesCourse)
			if (l.getLectureType().equals(NatureType.THEORY))
				theory += 1;
			else if (l.getLectureType().equals(NatureType.HANDS_ON))
				handsOn += 1;
		if (theory < handsOn)
			res = NatureType.HANDS_ON;
		else if (theory == handsOn)
			res = NatureType.BALANCED;

		return res;
	}

	default Integer totalNumberOfTheoryAudits(final Integer auditorId) {
		int res = 0;

		for (final Course course : this.findCourses())
			if (this.courseType(this.findAllLecturesByCourse(course.getId())) == NatureType.THEORY)
				res += this.countAuditsAuditorByCourse(course.getId(), auditorId);

		return res;
	}

	default Integer totalNumberOfHandsOnAudits(final Integer auditorId) {
		int res = 0;

		for (final Course course : this.findCourses())
			if (this.courseType(this.findAllLecturesByCourse(course.getId())) == NatureType.HANDS_ON)
				res += this.countAuditsAuditorByCourse(course.getId(), auditorId);

		return res;
	}

	@Query("select avg(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Double averageNumberOfAuditingRecords(@Param("id") int id);

	@Query("select count(ar) from AuditingRecord ar where ar.audit.auditor.id = :id group by ar.audit ")
	List<Integer> numberOfRecordsByAudit(@Param("id") int id);

	@Query("select min(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Double minimumNumberOfAuditingRecords(@Param("id") int id);

	@Query("select max(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Double maximumNumberOfAuditingRecords(@Param("id") int id);

	@Query("select ar from AuditingRecord ar where ar.audit.auditor.id = :id")
	List<AuditingRecord> findAllAuditingRecordsByAuditorId(@Param("id") int id);
	default Double averageTimeOfAuditingRecords(final int id) {
		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
		return records.stream().mapToDouble(AuditingRecord::getHoursFromStart).average().orElse(0);
	}

	default Double timeDeviationOfAuditingRecords(final int id) {
		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
		final List<Double> hours = records.stream().map(AuditingRecord::getHoursFromStart).collect(Collectors.toList());
		final double mean = hours.stream().mapToDouble(x -> x).sum() / hours.size();
		final Double deviation = Math.sqrt(hours.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / hours.size());
		return deviation;
	}

	default Double minimumTimeOfAuditingRecords(final int id) {
		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
		return records.stream().mapToDouble(AuditingRecord::getHoursFromStart).min().orElse(0);
	}

	default Double maximumTimeOfAuditingRecords(final int id) {
		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
		return records.stream().mapToDouble(AuditingRecord::getHoursFromStart).max().orElse(0);
	}

	default Double deviationOfAuditingRecords(final int id) {
		final List<Integer> numberOfRecords = this.numberOfRecordsByAudit(id);
		final double mean = numberOfRecords.stream().mapToDouble(x -> x).sum() / numberOfRecords.size();
		final Double deviation = Math.sqrt(numberOfRecords.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / numberOfRecords.size());
		return deviation;
	}
}
