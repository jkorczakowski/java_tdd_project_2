package UserApp;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javassist.NotFoundException;

public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> getUsers() {
        Set<User> userSet = new HashSet<>();
        userRepository.findAll().iterator().forEachRemaining(userSet::add);
        return userSet;
    }

    @Override
    public User findById(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new NotFoundException("ERROR: User not found."));
    }

    @Override
    public Iterable<User> findAll(Iterable<Long> users_id) {
        return userRepository.findAllById(users_id);
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        return userOptional.orElseThrow(() -> new NotFoundException("ERROR: User not found."));
    }

    @Override
    public Set<User> findUsersByName(String name) {
        Set<User> userSet = new HashSet<>();
        userRepository.findUsersByName(name).iterator().forEachRemaining(userSet::add);
        return userSet;
    }

    @Override
    public Set<User> findByNameContaining(String keyword) {
        Set<User> userSet = new HashSet<>();
        userRepository.findUsersByNameContaining(keyword).iterator().forEachRemaining(userSet::add);
        return userSet;
    }

    @Override
    public Set<User> findByEmailContaining(String keyword) {
        Set<User> userSet = new HashSet<>();
        userRepository.findUsersByEmailContaining(keyword).iterator().forEachRemaining(userSet::add);
        return userSet;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Iterable<User> saveAll(Iterable<User> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public void deleteById(Long idToDelete) {
        userRepository.deleteById(idToDelete);
    }

    @Override
    public void editUser (User oldUser, User editedUser) {
        oldUser.setName(editedUser.getName());
        oldUser.setPassword(editedUser.getPassword());
        oldUser.setEmail(editedUser.getEmail());
        userRepository.save(oldUser);
    }



    

    
}