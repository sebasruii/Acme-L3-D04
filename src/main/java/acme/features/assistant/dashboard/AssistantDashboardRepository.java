
package acme.features.assistant.dashboard;

import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {
	//
	//	@Query("select a from Assistant a where a.userAccount.id = :accountId")
	//	Assistant findAssistantByAccountId(@Param("accountId") int accountId);
	//
	//	@Query("select count(t) from Tutorial t where t.assistant.id = :id")
	//	Double totalNumberOfTutorials(@Param("id") int id);
	//
	//	@Query("select avg(select count(ts) from TutorialSession ts where ts.tutorial.id = a.id) from Tutorial t where t.assistant.id = :id")
	//	Double averageNumberOfTutorialSession(@Param("id") int id);
	//
	//	@Query("select count(ts) from TutorialSession ts where ts.tutorial.assistant.id = :id group by ts.tutorial ")
	//	List<Integer> numberOfSessionByTutorial(@Param("id") int id);
	//
	//	@Query("select min(select count(ts) from TutorialSession ts where ts.tutorial.id = t.id) from Tutorial t where t.assistant.id = :id")
	//	Double minimumNumberOfTutorialSession(@Param("id") int id);
	//
	//	@Query("select max(select count(ts) from TutorialSession ts where ts.tutorial.id = t.id) from Tutorial t where t.assistant.id = :id")
	//	Double maximumNumberOfTutorialSession(@Param("id") int id);
	//
	//	@Query("select ts from TutorialSession ts where ts.tutorial.assistant.id = :id")
	//	List<TutorialSession> findAllTutorialSessionByAssistantId(@Param("id") int id);
	//	default Double averageTimeOfTutorialSession(final int id) {
	//		final List<TutorialSession> ts = this.findAllTutorialSessionByAssistantId(id);
	//		return ts.stream().mapToDouble(TutorialSession::MomentHelper.computeDuration(getStartDate(),getFinishDate)getSeconds()).average().orElse(0);
	//	}
	//
	//	default Double timeDeviationOfAuditingRecords(final int id) {
	//		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
	//		final List<Double> hours = records.stream().map(AuditingRecord::getHoursFromStart).collect(Collectors.toList());
	//		final Double average = hours.stream().mapToDouble(Double::doubleValue).average().orElse(0);
	//		final List<Double> squaredDistancesToMean = hours.stream().map(h -> Math.pow(h - average, 2)).collect(Collectors.toList());
	//		final Double averageSquaredDistancesToMean = squaredDistancesToMean.stream().mapToDouble(Double::doubleValue).average().orElse(0);
	//		return Math.sqrt(averageSquaredDistancesToMean);
	//	}
	//
	//	default Double minimumTimeOfAuditingRecords(final int id) {
	//		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
	//		return records.stream().mapToDouble(AuditingRecord::getHoursFromStart).min().orElse(0);
	//	}
	//
	//	default Double maximumTimeOfAuditingRecords(final int id) {
	//		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
	//		return records.stream().mapToDouble(AuditingRecord::getHoursFromStart).max().orElse(0);
	//	}
	//
	//	default Double deviationOfAuditingRecords(final int id) {
	//		final List<Integer> numberOfRecords = this.numberOfRecordsByAudit(id);
	//		final Double average = numberOfRecords.stream().mapToInt(Integer::intValue).average().orElse(0);
	//		final List<Double> squaredDistancesToMean = numberOfRecords.stream().map(n -> Math.pow(n - average, 2.)).collect(Collectors.toList());
	//		final Double averageSquaredDistancesToMean = squaredDistancesToMean.stream().mapToDouble(Double::doubleValue).average().orElse(0);
	//		return Math.sqrt(averageSquaredDistancesToMean);
	//	}
}
