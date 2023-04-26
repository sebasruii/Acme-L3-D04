
package acme.features.assistant.tutorial;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.tutorialSessions.TutorialSession;
import acme.entities.tutorials.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findTutorialByCode(String code);

	@Query("select t from Tutorial t")
	Collection<Tutorial> findAllTutorials();

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :id")
	Collection<TutorialSession> findAllTutorialsSessionByTutorialId(int id);

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	@Query("select a from Assistant a where a.id = :assistantId")
	Assistant findAssistantById(int assistantId);

	@Query("select c from Course c where c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("select c from Course c where c.draftMode = false")
	List<Course> findAllPublishedCourses();

}
