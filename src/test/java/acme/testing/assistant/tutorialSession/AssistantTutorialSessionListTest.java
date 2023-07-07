
package acme.testing.assistant.tutorialSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class AssistantTutorialSessionListTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int TutorialRecordIndex, final int TutorialSessionRecordIndex, final String code, final String title, final String type, final String startDate, final String finishDate) {
		// HINT: this test authenticates as an employer, then lists his or her jobs, 
		// HINT+ selects one of them, and check that it has the expected duties.

		super.signIn("assistant2", "assistant2");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(TutorialRecordIndex, 0, code);
		super.clickOnListingRecord(TutorialRecordIndex);
		super.clickOnButton("Tutorial Session");

		super.checkListingExists();
		super.checkColumnHasValue(TutorialSessionRecordIndex, 0, title);
		super.checkColumnHasValue(TutorialSessionRecordIndex, 1, type);
		super.clickOnListingRecord(TutorialSessionRecordIndex);

		super.signOut();
	}

	//			@Test
	//			public void test200Negative() {
	//				// HINT: there's no negative test case for this listing, since it doesn't
	//				// HINT+ involve filling in any forms.
	//			}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list the duties of a job that is unpublished
		// HINT+ using a principal that didn't create it. 

		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
	}

}
