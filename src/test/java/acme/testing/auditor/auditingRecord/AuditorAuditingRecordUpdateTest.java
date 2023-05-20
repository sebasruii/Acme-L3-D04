
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing_record/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordRecordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String mark, final String link, final int recordAuditIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordAuditIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(recordRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update Record");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordRecordIndex, 0, subject);

		super.clickOnListingRecord(recordRecordIndex);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing_record/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordRecordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String mark, final String link, final int recordAuditIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordAuditIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(recordRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update Record");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<AuditingRecord> records;
		String param;

		records = this.repository.findAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord record : records)
			if (record.getDraftMode()) {
				param = String.format("id=%d", record.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}
}
