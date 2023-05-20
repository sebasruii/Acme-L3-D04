
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String goals, final boolean draftMode) {
		// HINT: this test authenticates as an employer and checks that he or
		// HINT+ she can display the expected announcements.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, summary);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to request the list of announcements as
		// HINT+ an anonymous principal.

		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
	}

}
