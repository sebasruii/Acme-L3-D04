
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing_record/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordRecordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String mark, final String link, final int recordAuditIndex) {

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordAuditIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(recordRecordIndex);
		super.clickOnSubmit("Delete Record");
		super.checkNotPanicExists();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to delete a session with a role other than "Assistant",
		// or using an assistant who is not the owner.
		Collection<AuditingRecord> records;
		String param;

		super.signIn("auditor1", "auditor1");
		records = this.repository.findAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord record : records)
			if (!record.getAudit().isDraftMode() && record.isDraftMode()) {
				param = String.format("auditId=%d", record.getAudit().getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/delete", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/auditor/auditing-record/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/auditor/auditing-record/delete", param);
				super.checkPanicExists();
				super.signOut();
			}

	}

	@Test
	public void test301Hacking() {
		Collection<AuditingRecord> records;
		String params;

		super.signIn("auditor1", "auditor1");
		records = this.repository.findAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord record : records)
			if (!record.getAudit().isDraftMode() && record.isDraftMode()) {
				params = String.format("auditId=%d", record.getAudit().getId());
				super.request("/auditor/auditing-record/delete", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		Collection<AuditingRecord> records;
		String params;

		super.signIn("auditor2", "auditor2");
		records = this.repository.findAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord record : records)
			if (!record.getAudit().isDraftMode() && record.isDraftMode()) {
				params = String.format("auditId=%d", record.getAudit().getId());
				super.request("/auditor/auditing-record/delete", params);
			}
		super.signOut();
	}
}
