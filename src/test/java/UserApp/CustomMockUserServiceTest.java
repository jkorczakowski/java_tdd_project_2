package UserApp;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javassist.NotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CustomMockUserServiceTest {
    UserRepository userRepository;
    UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = new MockUserRepository();
        userService = new UserServiceImp(userRepository);
    }

    @Test
    public void saveAllPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        User userDelta = new User("Zofia", "sapp", "zzien@gmail.com");

        HashSet<User> users = Sets.newHashSet(userAlpha, userBeta, userGamma, userDelta);
        userService.saveAll(users);
        Set<User> usersReturned = userService.getUsers();
        assertThat(usersReturned).isNotNull().isNotEmpty().hasSize(4).hasOnlyElementsOfType(User.class)
                .containsExactlyInAnyOrder(userAlpha, userBeta, userGamma, userDelta);

    }

    @Test
    public void saveAllNullPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        User userDelta = null;
        HashSet<User> users = Sets.newHashSet(userAlpha, userBeta, userGamma, userDelta);
        assertThatThrownBy(() -> userService.saveAll(users)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: User cannot be null");
    }

    @Test
    public void findUserByNameContainingPositiveTest() {
        String keyword = "ich";
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewich", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        HashSet<User> users = Sets.newHashSet(userAlpha, userBeta, userGamma);
        userService.saveAll(users);
        Set<User> usersReturned = userService.findByNameContaining(keyword);
        assertThat(usersReturned).isNotNull().isNotEmpty().isInstanceOf(Set.class).hasSize(2)
                .containsExactlyInAnyOrder(userAlpha, userBeta).hasOnlyElementsOfType(User.class);
    }

    @Test
    public void findUserByNameContainingEmptyPositiveTest() {
        String keyword = "trywa";
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewich", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        HashSet<User> users = Sets.newHashSet(userAlpha, userBeta, userGamma);
        userService.saveAll(users);
        Set<User> usersReturned = userService.findByNameContaining(keyword);
        assertThat(usersReturned).isNotNull().isEmpty();
    }

    @Test
    public void findUserByNameContainingNullPositiveTest() {
        String keyword = null;
        assertThatThrownBy(() -> userService.findByNameContaining(keyword)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Keyword cannot be null");
    }

    @Test
    public void findUserByEmailContainingPositiveTest() {
        String keyword = "ow";
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewich", "drowssaP", "ecelmow@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgramsow@gmail.com");
        HashSet<User> users = Sets.newHashSet(userAlpha, userBeta, userGamma);
        userService.saveAll(users);
        Set<User> usersReturned = userService.findByEmailContaining(keyword);
        assertThat(usersReturned).isNotNull().isNotEmpty().isInstanceOf(Set.class).hasSize(3)
                .containsExactlyInAnyOrder(userAlpha, userBeta, userGamma).hasOnlyElementsOfType(User.class);
    }

    @Test
    public void findUserByEmailContainingEmptyPositiveTest() {
        String keyword = "trywa";
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewich", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        HashSet<User> users = Sets.newHashSet(userAlpha, userBeta, userGamma);
        userService.saveAll(users);
        Set<User> usersReturned = userService.findByEmailContaining(keyword);
        assertThat(usersReturned).isNotNull().isEmpty();
    }

    @Test
    public void findUserByEmailContainingNullPositiveTest() {
        String keyword = null;
        assertThatThrownBy(() -> userService.findByEmailContaining(keyword))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("ERROR: Keyword cannot be null");
    }

    @Test
    public void countPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewich", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        HashSet<User> users = Sets.newHashSet(userAlpha, userBeta, userGamma);
        userService.saveAll(users);
        Set<User> usersReturned = userService.getUsers();
        assertThat(usersReturned.size()).isEqualTo(3);
    }

    @Test
    public void editUserPositiveTest() throws IllegalAccessException, InvocationTargetException, NotFoundException {
        User oldUser = new User("Michał", "Password", "mkow@gmail.com");
        User newUser = new User ("Ewa", "Drowssap", "ecelm@gmail.com");
        
        userService.save(oldUser);
        userService.editUser(oldUser, newUser);
        Set<User> usersReturned = userService.getUsers();
        User checkUser = userService.findByEmail("ecelm@gmail.com");
        
        assertAll("Conditions",
        ()-> assertThat(usersReturned).isNotNull().isNotEmpty().hasSize(1),
        ()-> assertThat(checkUser.getName()).isEqualTo("Ewa"),
        ()-> assertThat(checkUser.getPassword()).isEqualTo("Drowssap"),
        ()-> assertThat(checkUser.getEmail()).isEqualTo("ecelm@gmail.com")
        );
    }

    @Test
    public void editUserNullPositiveTest() {
        assertThatThrownBy(()->userService.editUser(null, null)).isInstanceOf(Exception.class);
    }



    

    @AfterEach
    void tearDown() {
        userRepository = null;
        userService = null;
    }
    
}