
package acme.testing.assistant.tutorial;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String goals, final boolean draftMode, final String estimatedTotalTime, final String course) {
		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Assistant", "Tutorials List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, summary);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		super.checkInputBoxHasValue("course", "course-01");

		super.signOut();

	}
	//
	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/lecturer/lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test200Negative(final int recordIndex, final String title, final String summary, final String estimatedLearningTime, final String body, final String lectureType, final String link) {
	//		super.signIn("lecturer1", "lecturer1");
	//
	//		super.clickOnMenu("Lecturer", "My lectures");
	//		super.checkListingExists();
	//
	//		super.clickOnButton("Create");
	//		super.fillInputBoxIn("title", title);
	//		super.fillInputBoxIn("summary", summary);
	//		super.fillInputBoxIn("estimatedLearningTime", estimatedLearningTime);
	//		super.fillInputBoxIn("body", body);
	//		super.fillInputBoxIn("lectureType", lectureType);
	//		super.fillInputBoxIn("link", link);
	//		super.clickOnSubmit("Create");
	//
	//		super.checkErrorsExist();
	//
	//		super.signOut();
	//	}
	//
	//	@Test
	//	public void test300Hacking() {
	//		// HINT: this test tries to create a Lecture using principals with
	//		// HINT+ inappropriate roles.
	//
	//		super.checkLinkExists("Sign in");
	//		super.request("/lecturer/lecture/create");
	//		super.checkPanicExists();
	//
	//		super.signIn("administrator1", "administrator1");
	//		super.request("/lecturer/lecture/create");
	//		super.checkPanicExists();
	//		super.signOut();
	//
	//		super.signIn("student1", "student1");
	//		super.request("/employer/job/create");
	//		super.checkPanicExists();
	//		super.signOut();
	//	}

}
