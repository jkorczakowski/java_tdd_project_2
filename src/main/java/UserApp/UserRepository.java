package UserApp;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String name);
    Set<User> findUsersByName(String name);
    Set<User> findUsersByNameContaining(String keyword);
    Set<User> findUsersByEmailContaining(String keyword);
    void editUser(User oldUser, User newUser);
    
}