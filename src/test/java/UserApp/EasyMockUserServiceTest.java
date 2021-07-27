package UserApp;

import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javassist.NotFoundException;

import static org.easymock.EasyMock.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EasyMockUserServiceTest {
    private UserRepository userRepository;

    @TestSubject
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = createNiceMock(UserRepository.class);
        userService = new UserServiceImp(userRepository);
    }

    @AfterEach
    void tearDown() {
        userService = null;
        userRepository = null;

    }

    @Test
    public void findUserByEmailPositiveTest() throws NotFoundException {
        User user = new User("Michał", "Password", "mkow@gmail.com");
        Optional<User> userOptional = Optional.of(user);
        expect(userRepository.findUserByEmail("mkow@gmail.com")).andReturn(userOptional);
        replay(userRepository);
        User userReturned = userService.findByEmail("mkow@gmail.com");
        assertThat(userReturned).isNotNull().isInstanceOf(User.class).isEqualTo(user);
        verify(userRepository);
    }

    @Test
    public void findUserByEmailNotFoundPositiveTest() throws NotFoundException {
        Optional<User> userOptional = Optional.empty();
        expect(userRepository.findUserByEmail("mkow@gmail.com")).andReturn(userOptional);
        replay(userRepository);
        assertThatThrownBy( () -> userService.findByEmail("mkow@gmail.com")).isInstanceOf(NotFoundException.class)
        .hasMessage("ERROR: User not found.");
        verify(userRepository);
    }

    @Test
    public void findUserByEmailNullExceptionPositiveTest() {
        expect(userRepository.findUserByEmail(null)).andThrow(new IllegalArgumentException("ERROR: Email cannot be null"));
        replay(userRepository);
        assertThatThrownBy( () -> userService.findByEmail(null)).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ERROR: Email cannot be null");
        verify(userRepository);

    }

    @Test
    public void findUserByEmailEmptyPositive() throws NotFoundException {
        expect(userRepository.findUserByEmail("")).andReturn(Optional.empty());
        replay(userRepository);
        assertThatThrownBy( () -> userService.findByEmail("")).isInstanceOf(NotFoundException.class)
        .hasMessage("ERROR: User not found.");
        verify(userRepository);
    }

    @Test
    public void getUsersPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        User userDelta = new User("Zofia", "sapp", "zzien@gmail.com");

        HashSet<User> userSet = new HashSet<>();
        userSet.add(userAlpha);
        userSet.add(userBeta);
        userSet.add(userGamma);
        userSet.add(userDelta);

        expect(userRepository.findAll()).andReturn(userSet);
        replay(userRepository);

        Set<User> users = userService.getUsers();
        assertThat(users).isNotNull().isNotEmpty().hasSize(4).isInstanceOf(Set.class)
        .containsExactlyInAnyOrder(userAlpha, userBeta, userGamma, userDelta);
        verify(userRepository);
    }

    @Test
    public void saveUserPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        expect(userRepository.save(userAlpha)).andReturn(userAlpha);
        replay(userRepository);
        User savedUser = userService.save(userAlpha);
        assertThat(savedUser).isNotNull().isInstanceOf(User.class).isEqualTo(userAlpha);
        verify(userRepository);
    }

    @Test
    public void saveUserNullPositiveTest() {
        User user = null;
        expect(userRepository.save(user)).andThrow(new IllegalArgumentException("ERROR: User cannot be null"));
        replay(userRepository);
        assertThatThrownBy( () -> userService.save(user)).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ERROR: User cannot be null");
        verify(userRepository);
    }

    @Test
    public void deleteByIdUserPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        User userDelta = new User("Zofia", "sapp", "zzien@gmail.com");

        HashSet<User> userSet = new HashSet<>();
        userSet.add(userAlpha);
        userSet.add(userBeta);
        userSet.add(userGamma);
        userSet.add(userDelta);

        userService.deleteById(userBeta.getId());
        expectLastCall().andAnswer(() -> userSet.remove(userBeta));
        replay(userRepository);
        userService.deleteById(userBeta.getId());
        assertThat(userSet).hasSize(3);
        verify(userRepository);
    }
    @Test
    public void deleteByIdNullPositiveTest() {
        userService.deleteById(null);
        expectLastCall().andThrow(new IllegalArgumentException("ERROR: Id cannot be null"));
        replay(userRepository);
        assertThatThrownBy( ()-> userService.deleteById(null)).isInstanceOf(IllegalArgumentException.class).
        hasMessage("ERROR: Id cannot be null");
        verify(userRepository);
    }

    

   
    
    
}