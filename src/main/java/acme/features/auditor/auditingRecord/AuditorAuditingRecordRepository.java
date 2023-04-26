
package acme.features.auditor.auditingRecord;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("select ar from AuditingRecord ar where ar.audit.id=:masterId")
	List<AuditingRecord> findAllAuditingRecordsByMasterId(int masterId);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select ar.audit from AuditingRecord ar where ar.id=:id")
	Audit findOneAuditByAuditingRecordId(int id);

	@Query("select ar from AuditingRecord ar where ar.id=:id")
	AuditingRecord findOneAuditingRecordById(int id);

	@Query("select a from Audit a")
	Collection<Audit> findAllAudits();
}
