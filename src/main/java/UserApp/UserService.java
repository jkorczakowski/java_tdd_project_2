package UserApp;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javassist.NotFoundException;

public interface UserService {
    
    User findById(Long id) throws NotFoundException;
    User findByEmail(String email) throws NotFoundException;
    User save(User user);

    Set<User> getUsers();
    Set<User> findUsersByName(String name);
    Set<User> findByNameContaining(String keyword);
    Set<User> findByEmailContaining(String keyword);
   
    Iterable<User> findAll(Iterable<Long> users_id);
    Iterable<User> saveAll(Iterable<User> users);
    void deleteById(Long idToDelete);
    void editUser(User oldUser, User newUser) throws IllegalAccessException, InvocationTargetException, NotFoundException;
    
}