
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorials.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String goals, final boolean draftMode) {
		// HINT: this test signs in as an employer, then lists the announcements,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("goals", goals);

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findTutorialsByAssistant("assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();
			super.signOut();

			if (tutorial.isDraftMode()) {
				super.signIn("assistant1", "assistant1");
				super.request("/assistant/tutorial/show", param);
				super.checkPanicExists();
				super.signOut();
			}

		}

	}

}
