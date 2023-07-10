
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordShowTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing_record/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordRecordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String mark, final String link, final int recordAuditIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordAuditIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(recordRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	@Test
	public void test300hacking() {
		Collection<AuditingRecord> records;
		String param;

		records = this.repository.findAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord record : records)
			if (record.isDraftMode()) {
				param = String.format("id=%d", record.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}
}
