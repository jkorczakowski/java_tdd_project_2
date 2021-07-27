package UserApp;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javassist.NotFoundException;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class MockitoUserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImp(userRepository);
    }

    
    @Test
    public void findUserByIdPositiveTest() throws NotFoundException {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");
        User userGamma = new User("Stanisław", "pass", "sgrams@gmail.com");
        User userDelta = new User("Zofia", "sapp", "zzien@gmail.com");
        Optional<User> userOptional = Optional.of(userDelta);
        when(userRepository.findById(4L)).thenReturn(userOptional);
        User userReturned = userService.findById(4L);
        assertThat(userReturned).isNotNull().isInstanceOf(User.class).isEqualTo(userDelta);
        verify(userRepository).findById(4L);
        verify(userRepository, never()).findAll();
    }

    @Test
    public void findUserByIdNotFoundExceptionPositiveTest() throws NotFoundException {
        Optional<User> userOptional = Optional.empty();
        when(userRepository.findById(1L)).thenReturn(userOptional);
        assertThatThrownBy(() -> userService.findById(1L)).isInstanceOf(NotFoundException.class).hasMessage("ERROR: User not found.");
    }

    @Test
    public void findUserByIdNullExceptionPositiveTest() {
        when(userRepository.findById(null)).thenThrow(new IllegalArgumentException("ERROR: Id cannot be null"));
        assertThatThrownBy(() -> userService.findById(null)).isInstanceOf(IllegalArgumentException.class).hasMessage("ERROR: Id cannot be null");
    }

    @Test
    public void findAllPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");
        HashSet<User> userSet = new HashSet<>(Arrays.asList(userAlpha, userBeta));
        List<Long> ids = Arrays.asList(1L, 2L);
        when(userRepository.findAllById(ids)).thenReturn(userSet);
        Iterable<User> userReturned = userService.findAll(ids);
        assertThat(userReturned).isNotNull().isNotEmpty().hasOnlyElementsOfType(User.class)
        .hasSize(2).containsExactlyInAnyOrder(userAlpha, userBeta);
        verify(userRepository).findAllById(ids);
    }

    @Test
    public void findAllPartialPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");
        HashSet<User> userSet = new HashSet<>(Arrays.asList(userAlpha, userBeta));
        List<Long> ids = Collections.singletonList(2L);
        when(userRepository.findAllById(ids)).thenReturn(new HashSet<>(Collections.singletonList(userBeta)));
        Iterable<User> usersReturned = userService.findAll(ids);
        assertThat(usersReturned).isNotNull().isNotEmpty().hasOnlyElementsOfType(User.class)
        .hasSize(1).isNotEqualTo(userSet).containsExactly(userBeta);
        verify(userRepository).findAllById(ids);
    }

    @Test
    public void findAllNullPositiveTest() {
        List<Long> ids = Arrays.asList(1L, null, 2L);
        when(userRepository.findAllById(ids)).thenThrow(new IllegalArgumentException("ERROR: Id cannot be null"));
        assertThatThrownBy(() -> userService.findAll(ids)).isInstanceOf(IllegalArgumentException.class).hasMessage("ERROR: Id cannot be null");
    }

    @Test
    public void findUserByNamePositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        // User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");

        HashSet userSet = new HashSet<>();
        userSet.add(userAlpha);
        // userSet.add(userBeta);

        when(userRepository.findUsersByName("Michał")).thenReturn(userSet);
        Set<User> users = userService.findUsersByName("Michał");
        assertThat(users).isNotNull().isNotEmpty().hasSize(1).containsOnly(userAlpha).isInstanceOf(Set.class);
        verify(userRepository).findUsersByName("Michał");
        verify(userRepository, never()).findAll();
        verify(userRepository, never()).findById(1L);

    }

    @Test
    public void findUserByNameEmptyPositiveTest() {
        User userAlpha = new User("Michał", "Password", "mkow@gmail.com");
        // User userBeta = new User("Ewa", "drowssaP", "ecelm@gmail.com");

        HashSet userSet = new HashSet<>();
        userSet.add(userAlpha);
        // userSet.add(userBeta);

        when(userRepository.findUsersByName("Ewa")).thenReturn(new HashSet<>());
        Set<User> users = userService.findUsersByName("Ewa");
        assertThat(users).isNotNull().isEmpty();
        verify(userRepository).findUsersByName("Ewa");
        verify(userRepository, never()).findAll();
        verify(userRepository, never()).findById(1L);
    }

    @Test
    public void findUserByNameNullPositiveTest() {
        when(userRepository.findUsersByName(null)).thenThrow(new IllegalArgumentException("ERROR: Name cannot be null"));
        assertThatThrownBy( () -> userService.findUsersByName(null)).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ERROR: Name cannot be null");
        verify(userRepository).findUsersByName(null);
        verify(userRepository, never()).findAll();
        verify(userRepository, never()).findById(anyLong());
    }





    
}