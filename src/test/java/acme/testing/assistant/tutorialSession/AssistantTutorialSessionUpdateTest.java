
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorialSessions.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final int tutorialSessionRecordIndex, final String title, final String summary, final String type, final String startDate, final String finishDate, final String link) {

		super.signIn("assistant2", "assistant2");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Tutorial Session");
		super.checkListingExists();
		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

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
	@CsvFileSource(resources = "/assistant/tutorialSession/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorialRecordIndex, final int tutorialSessionRecordIndex, final String title, final String summary, final String type, final String startDate, final String finishDate, final String link) {

		super.signIn("assistant2", "assistant2");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Tutorial Session");
		super.checkListingExists();
		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<TutorialSession> tutorialSession;
		String param;

		tutorialSession = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession ts : tutorialSession)
			if (ts.isDraftMode()) {
				param = String.format("masterId=%d", ts.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
