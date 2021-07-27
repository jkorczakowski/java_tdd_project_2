package UserApp;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class UserTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    User user;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @AfterAll
    static void close() { 
        validatorFactory.close();
    }

    //  Id tests
    @Test
    public void JU5GetSetIdNotNullTest() {
        Long id = 7L;
        user.setId(id);
        assertNotNull(user.getId());
    }

    @Test
    public void JU5GetIdNullTest() {
        assertNull(user.getId());
    }

    @Test
    public void JU5GetSetIdPositiveTest() {
        Long id = 7L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void JU5GetSetIdNegativeTest() {
        Long id = 7L;
        user.setId(id);
        assertNotEquals(3L, user.getId());
    }

    //  Name testes
    @Test
    public void JU5GetSetNameNotNullTest() {
        String name = "not null";
        user.setName(name);
        assertNotNull(user.getName());
    }

    @Test
    public void JU5GetNameNullTest() {
        assertNull(user.getName());
    }

    @Test
    public void JU5GetSetNamePositiveTest() {
        String name = "Michał";
        user.setName(name);
        assertEquals(name, user.getName());
    }

    @Test
    public void JU5GetSetNameNegativeTest() {
        String name = "Michał";
        user.setName(name);
        assertNotEquals("Ewa", user.getName());
    }

    @Test
    public void JU5NameInvalidNameNullInputPositiveTest() {
        User user = new User(null, "qwerty", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: NULL")));
    }

    @Test
    public void JU5NameInvalidNameNullInputNegativeTest() {
        User user = new User("Not null 21", "qwerty", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertEquals(0, errorCodes.size());
    }

    @Test
    public void JU5NameInvalidNameEmptyInputPositiveTest() {
        User user = new User("", "qwerty", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: TOO SHORT | TOO LONG | EMPTY. Proper length: 3-15.")));
    }

    @Test
    public void JU5NameInvalidNameTooShortInputPositiveTest() {
        User user = new User("ab", "qwerty", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: TOO SHORT | TOO LONG | EMPTY. Proper length: 3-15.")));
    }

    @Test
    public void JU5NameInvalidNameTooLongInputPositiveTest() {
        User user = new User("abcdefghijklmnoprstqwxyz", "qwerty", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: TOO SHORT | TOO LONG | EMPTY. Proper length: 3-15.")));
    }

    public void JU5NameInvalidNamePatternInputPositiveTest() {
        User user = new User("x))));OOOO", "qwerty", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: PATTERN ERROR. Allowed only: A-z, 0-9, polish letters, spaces.")));
    }

    @Test
    public void JU5NameInvalidNameEmptyLengthPatterInputNegativeTest() {
        User user = new User("Everything ok", "qwerty", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        
       assertEquals(0, errorCodes.size());
    }







    //  Password tests
    @Test
    public void JU5GetSetPasswordNotNullTest() {
        String pass = "not null";
        user.setPassword(pass);
        assertNotNull(user.getPassword());
    }

    @Test
    public void JU5GetPasswordNullTest() {
        assertNull(user.getPassword());
    }
    
    @Test
    public void JU5GetSetPasswordPositiveTest() {
        String pass = "goodpassword123";
        user.setPassword(pass);
        assertEquals(pass, user.getPassword());
    }

    @Test
    public void JU5GetSetPasswordNegativeTest() {
        String pass = "goodpassword123";
        user.setPassword(pass);
        assertNotEquals("not this password", user.getPassword());
    }

    @Test
    public void JU5PasswordInvalidPasswordNullInputPositiveTest() {
        User user = new User("Name", null, "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: NULL")));
    }

    @Test
    public void JU5PasswordInvalidPasswordNullInputNegativeTest() {
        User user = new User("Name", "passwordxD", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertEquals(0, errorCodes.size());
    }

    @Test
    public void JU5PasswordInvalidPasswordEmptyInputPositiveTest() {
        User user = new User("Name", "", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: TOO SHORT | TOO LONG | EMPTY. Proper length: 6-30.")));
    }

    @Test
    public void JU5PasswordInvalidPasswordTooShortInputPositiveTest() {
        User user = new User("Name", "pass", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: TOO SHORT | TOO LONG | EMPTY. Proper length: 6-30.")));
    }

    @Test
    public void JU5PasswordInvalidPasswordTooLongInputPositiveTest() {
        User user = new User("Name", "abcdefghijklmnoprstqwxyzabcdefghijklmnoprstqwxyz", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: TOO SHORT | TOO LONG | EMPTY. Proper length: 6-30.")));
    }

    @Test
    public void JU5PasswordInvalidPasswordEmptyLengthPatterInputNegativeTest() {
        User user = new User("Name", "ok password", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        
       assertEquals(0, errorCodes.size());
    }


    //  Email tests
    @Test
    public void JU5GetSetEmailNotNullTest() {
        String email = "not null";
        user.setEmail(email);
        assertNotNull(user.getEmail());
    }

    @Test
    public void JU5GetEmailNullTest() {
        assertNull(user.getEmail());
    }

    @Test
    public void JU5GetSetEmailPositiveTest() {
        String email = "mkow@gmail.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void JU5GetSetEmailNegativeTest() {
        String email = "mkow@gmail.com";
        user.setEmail(email);
        assertNotEquals("Not this email", user.getEmail());
    }

    @Test
    public void JU5EmailInvalidEmailNullInputPositiveTest() {
        User user = new User("Name", "Password", null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: NULL")));
    }

    @Test
    public void JU5EmailInvalidEmailNullInputNegativeTest() {
        User user = new User("Name", "Password", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertEquals(0, errorCodes.size());
    }

    @Test
    public void JU5EmailInvalidEmailPatternInputPositiveTest() {
        User user = new User("Name", "Password", "notemail");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

        assertAll("Conditions",
        ()-> assertNotEquals(0, errorCodes.size()),
        ()-> assertTrue(errorCodes.contains("Forbidden input: EMAIL NOT VALID")));
    }

    public void JU5EmailInvalidEmailPatternInputNegativeTest() {
        User user = new User("Name", "Password", "mkow@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        List<String> errorCodes = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations)
            errorCodes.add(violation.getMessage());

            assertEquals(0, errorCodes.size());
    }



    
}