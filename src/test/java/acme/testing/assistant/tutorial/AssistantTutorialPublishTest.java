
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorials.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialPublishTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {
		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);

		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findTutorialsByAssistant("assistant2");
		for (final Tutorial tutorial : tutorials)
			if (tutorial.isDraftMode()) {
				param = String.format("id=%d", tutorial.getId());

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

	@Test
	public void test301Hacking() {
		Collection<Tutorial> tutorials;
		String param;

		super.signIn("assistant2", "assistant2");
		tutorials = this.repository.findTutorialsByAssistant("assistant2");
		for (final Tutorial tutorial : tutorials)
			if (!tutorial.isDraftMode()) {
				param = String.format("id=%d", tutorial.getId());
				super.request("/assistant/tutorial/publish", param);
				super.checkPanicExists();

			}
		super.signOut();

	}

	@Test
	public void test302Hacking() {
		Collection<Tutorial> tutorials;
		String param;

		super.signIn("assistant2", "assistant2");
		tutorials = this.repository.findTutorialsByAssistant("assistant1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/publish", param);
			super.checkPanicExists();

		}

	}

}
