package userService.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long AttendanceSalaryID;
    String userID;
    String userName;
    int month;
    int year;
    Double sumTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigDecimal salary;
}
