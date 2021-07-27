// Model class for user
package UserApp;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "db_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Forbidden input: NULL")
    @Size(min = 3, max = 15, message = "Forbidden input: TOO SHORT | TOO LONG | EMPTY. Proper length: 3-15.")
    @Pattern(regexp = "[A-Za-z0-9żźćńółęąśŻŹĆĄŚĘŁÓŃ ]*",    message = "Forbidden input: PATTERN ERROR. Allowed only: A-z, 0-9, polish letters, spaces.")
    private String name;

    @Column(unique = false)
    @NotNull(message = "Forbidden input: NULL")
    @Size(min = 6, max = 30, message = "Forbidden input: TOO SHORT | TOO LONG | EMPTY. Proper length: 6-30.")
    private String password;

    @Column(unique = true)
    @NotNull(message = "Forbidden input: NULL")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Forbidden input: EMAIL NOT VALID")
    private String email;

    public User(){

    }

    public User(@NotNull @Size(min = 4, max = 15) String name, @NotNull @Size(min = 6, max = 60) String password,
     @NotNull @Email String email){
         this.name = name;
         this.password = password;
         this.email = email;

    }

}