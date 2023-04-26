
package acme.features.any.peep;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.peeps.Peep;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("select p from Peep p")
	List<Peep> findAllPeeps();

	@Query("select p from Peep p where p.id = :id")
	Peep findPeepById(@Param("id") int id);

}
