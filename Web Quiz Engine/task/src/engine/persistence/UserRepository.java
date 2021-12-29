package engine.persistence;

import engine.businesslayer.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsByUsername(String username);
    User findUserByUsername(String username);
}
