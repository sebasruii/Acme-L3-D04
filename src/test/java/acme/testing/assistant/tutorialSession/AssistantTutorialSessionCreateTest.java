
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorialSessions.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final int tutorialSessionRecordIndex, final String title, final String summary, final String type, final String startDate, final String finishDate, final String link) {
		// HINT: this test authenticates as an employer, list his or her jobs, navigates
		// HINT+ to their duties, and checks that they have the expected data.

		super.signIn("assistant2", "assistant2");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Tutorial Session");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(tutorialSessionRecordIndex, 0, title);
		super.checkColumnHasValue(tutorialSessionRecordIndex, 1, type);
		super.checkColumnHasValue(tutorialSessionRecordIndex, 2, startDate);
		super.checkColumnHasValue(tutorialSessionRecordIndex, 3, finishDate);

		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorialRecordIndex, final int tutorialSessionRecordIndex, final String title, final String summary, final String type, final String startDate, final String finishDate, final String link) {
		// HINT: this test attempts to create duties using wrong data.
		super.signIn("assistant2", "assistant2");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Tutorial Session");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a duty for a job as a principal without 
		// HINT: the "Employer" role.

		Collection<TutorialSession> tutorialSession;
		String param;

		tutorialSession = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession ts : tutorialSession) {
			param = String.format("masterId=%d", ts.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a duty for a published job created by 
		// HINT+ the principal.

		Collection<TutorialSession> tutorialsSession;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		tutorialsSession = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession ts : tutorialsSession)
			if (!ts.isDraftMode()) {
				param = String.format("masterId=%d", ts.getId());
				super.request("/assistant/tutorial-session/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create duties for jobs that weren't created 
		// HINT+ by the principal.

		Collection<TutorialSession> tutorialSession;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		tutorialSession = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession ts : tutorialSession) {
			param = String.format("masterId=%d", ts.getId());
			super.request("/assistant/tutorial-session/create", param);
			super.checkPanicExists();
		}
	}

}
