
package acme.features.auditor.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.id = :auditorId")
	List<Audit> findAllAuditsByAuditorId(int auditorId);

	@Query("select a from Audit a where a.id = :id")
	Audit findAuditById(int id);

	@Query("select ar.mark from AuditingRecord ar where ar.audit.id = :id")
	List<String> findAllMarksByAuditId(int id);

	@Query("select a from Auditor a where a.userAccount.id = :accountId")
	Auditor findAuditorByAccountId(int accountId);

	@Query("select a from Auditor a where a.id = :auditorId")
	Auditor findOneAuditorById(int auditorId);

	@Query("select c from Course c where c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("select a from Audit a where a.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("select count(a)>0 from Audit a where a.code = :code and a.id != :id")
	boolean existsAuditWithCode(String code, int id);

	@Query("select c from Course c where c.draftMode = false")
	List<Course> findAllPublishedCourses();

	@Query("select ar from AuditingRecord ar where ar.audit.id = :id")
	List<AuditingRecord> findAllAuditingRecordsByAuditId(int id);

}
