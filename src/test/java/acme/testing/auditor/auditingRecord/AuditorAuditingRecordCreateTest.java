
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	// Test methods -----------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing_record/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordRecordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String mark, final String link, final int recordAuditIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordAuditIndex);

		super.clickOnButton("Add Auditing Record");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create Record");

		super.clickOnButton("Auditing Records");
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
	@CsvFileSource(resources = "/auditor/auditing_record/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200negative(final int recordRecordIndex, final String subject, final String assessment, final String startDate, final String finishDate, final String mark, final String link, final int recordAuditIndex) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordAuditIndex);

		super.clickOnButton("Add Auditing Record");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create Record");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits) {
			param = String.format("auditId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

	@Test
	public void test301Hacking() {

		Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (!audit.isDraftMode()) {
				param = String.format("masterId=%d", audit.getId());
				super.request("/audit/auditing_record/create", param);
				super.checkPanicExists();
			}
	}

}
