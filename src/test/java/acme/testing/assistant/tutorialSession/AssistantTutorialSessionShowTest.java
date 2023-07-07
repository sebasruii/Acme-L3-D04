
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorialSessions.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int TutorialRecordIndex, final int TutorialSessionRecordIndex, final String title, final String summary, final String type, final String startDate, final String finishDate, final String link) {
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

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show a duty of a job that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<TutorialSession> tutorialsSession;
		String param;

		tutorialsSession = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession tutorialSession : tutorialsSession)
			if (tutorialSession.isDraftMode()) {
				param = String.format("masterId=%d", tutorialSession.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();
				super.signOut();
			} else if (!tutorialSession.isDraftMode()) {
				param = String.format("masterId=%d", tutorialSession.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
