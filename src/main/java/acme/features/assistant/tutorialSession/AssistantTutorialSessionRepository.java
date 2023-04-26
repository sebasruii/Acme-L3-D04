
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorialSessions.TutorialSession;
import acme.entities.tutorials.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select s from TutorialSession s where s.tutorial.id = :masterId")
	Collection<TutorialSession> findManySessionsByMasterId(int masterId);

	@Query("select s.tutorial from TutorialSession s where s.id = :id")
	Tutorial findOneTutorialByTutorialSessionId(int id);

	@Query("select s from TutorialSession s where s.id = :id")
	TutorialSession findOneTutorialSessionById(int id);

}
