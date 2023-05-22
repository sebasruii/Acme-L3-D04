
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorialSessions.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionPublishTest extends TestHarness {

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int TutorialRecordIndex, final int TutorialSessionRecordIndex, final String title, final String startDate, final String finishDate, final String type) {
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
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);

		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		Collection<TutorialSession> ts;
		String param;

		ts = this.repository.findManyTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession tutorialSession : ts)
			if (tutorialSession.isDraftMode()) {
				param = String.format("id=%d", tutorialSession.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial/publish", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/assistant/tutorial/publish", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/assistant/tutorial/publish", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

	//	@Test
	//	public void test301Hacking() {
	//		Collection<Tutorial> tutorials;
	//		String param;
	//
	//		super.signIn("assistant2", "assistant2");
	//		tutorials = this.repository.findTutorialsByAssistant("assistant2");
	//		for (final Tutorial tutorial : tutorials)
	//			if (!tutorial.isDraftMode()) {
	//				param = String.format("id=%d", tutorial.getId());
	//				super.request("/assistant/tutorial/publish", param);
	//				super.checkPanicExists();
	//
	//			}
	//		super.signOut();
	//
	//	}
	//
	//	@Test
	//	public void test302Hacking() {
	//		Collection<Tutorial> tutorials;
	//		String param;
	//
	//		super.signIn("assistant2", "assistant2");
	//		tutorials = this.repository.findTutorialsByAssistant("assistant1");
	//		for (final Tutorial tutorial : tutorials) {
	//			param = String.format("id=%d", tutorial.getId());
	//			super.request("/assistant/tutorial/publish", param);
	//			super.checkPanicExists();
	//
	//		}
	//
	//	}

}
