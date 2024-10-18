package userService.test.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userID;
    String userName;
    String userPhone;
    String userMail;
    LocalDate dbo;
    String passWord;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

}
