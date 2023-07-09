
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorialSessions.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionDeleteTest extends TestHarness {

	// Internal State -----------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int TutorialRecordIndex, final int TutorialSessionRecordIndex) {
		// HINT: this test signs in as an employer, lists his or her jobs, selects
		// HINT+ one of them and checks that it's as expected.

		super.signIn("assistant2", "assistant2");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(TutorialRecordIndex);
		super.clickOnButton("Tutorial Session");
		super.checkListingExists();
		super.clickOnListingRecord(TutorialSessionRecordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Delete");

		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show a duty of a job that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		final Collection<TutorialSession> tutorialSession;
		String param;

		tutorialSession = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession ts : tutorialSession)
			if (ts.isDraftMode()) {
				param = String.format("masterId=%d", ts.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial-session/delete", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/assistant/tutorial-session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/assistant/tutorial-session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/assistant/tutorial-session/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
