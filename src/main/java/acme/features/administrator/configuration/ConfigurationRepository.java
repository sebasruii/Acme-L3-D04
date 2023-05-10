
package acme.features.administrator.configuration;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ConfigurationRepository extends AbstractRepository {

	@Query("select c from Configuration c")
	Configuration findConfiguration();

	@Query("select price.currency from Course")
	Set<String> findManyCurrenciesFromCourses();

}
