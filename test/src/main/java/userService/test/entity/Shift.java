package userService.test.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Shift {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    int shiftID;
    String shiftName;
    LocalTime startTime;
    LocalTime endTime;
    Date createdAt;
    Date updateAt;
}
