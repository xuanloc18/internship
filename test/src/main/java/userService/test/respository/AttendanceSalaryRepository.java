package userService.test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.test.entity.AttendanceSalary;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceSalaryRepository extends JpaRepository<AttendanceSalary,Long> {
    @Query("SELECT a FROM AttendanceSalary a WHERE a.userID = :userId " +
            "AND a.year = :year AND a.month = :month")
    Optional<AttendanceSalary> findAttendanceSalary(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

    @Query("SELECT a FROM AttendanceSalary a WHERE a.month = :month AND a.year = :year")
    List<AttendanceSalary> findListAttendanceSalary(@Param("month") int month, @Param("year") int year);

}
