
package acme.features.authenticated.student;

import org.springframework.data.jpa.repository.Query;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

public interface AuthenticatedStudentRepository extends AbstractRepository {

	@Query("select a from Student a where a.userAccount.id = :id")
	Student findOneStudentByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
