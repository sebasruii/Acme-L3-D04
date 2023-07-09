
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorialSessions.TutorialSession;
import acme.entities.tutorials.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialSessionTestRepository extends AbstractRepository {

	@Query("select ts from TutorialSession ts where ts.tutorial.assistant.userAccount.username = :username")
	Collection<TutorialSession> findManyTutorialSessionsByAssistantUsername(String username);

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :assistant")
	Collection<Tutorial> findTutorialsByAssistant(String assistant);

}
