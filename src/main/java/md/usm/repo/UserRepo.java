package md.usm.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import md.usm.model.user.User;

@Repository
public interface UserRepo extends CrudRepository<User, String> {

    User findByEmail(final String email);
}
