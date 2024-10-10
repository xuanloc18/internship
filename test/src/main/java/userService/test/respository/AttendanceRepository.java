package userService.test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.test.entity.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,String> {
    @Query("SELECT a FROM Attendance a WHERE a.workDate = :workDate AND a.userID = :userId")
    Optional<Attendance> findByWorkDateAndUserId(@Param("workDate") LocalDate workDate, @Param("userId") Long userId);
    @Query("SELECT a FROM Attendance a WHERE  a.userID = :userId")
    List<Attendance> findByUserId( @Param("userId") Long userId);
    // Đếm tổng số ngày làm việc theo tháng và năm
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.userID = :userId " +
            "AND YEAR(a.workDate) = :year AND MONTH(a.workDate) = :month AND a.checkInTime IS NOT NULL")
    int countTotalDaysWorkedInMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

    // Tính tổng số giờ làm việc theo tháng và năm
    @Query("SELECT SUM(a.bigDecimal) FROM Attendance a " +
            "WHERE a.userID = :userId " +
            "AND YEAR(a.workDate) = :year AND MONTH(a.workDate) = :month " +
            "AND a.checkInTime IS NOT NULL AND a.checkOutTime IS NOT NULL")
    Long sumTotalHoursWorkedInMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);
    // Tìm chấm công theo userId, tháng và năm
    @Query("SELECT a FROM Attendance a WHERE a.userID = :userId " +
            "AND FUNCTION('YEAR', a.workDate) = :year AND FUNCTION('MONTH', a.workDate) = :month")
    List<Attendance> findByUserIdAndMonthAndYear(@Param("userId") Long userId,
                                                 @Param("year") int year,
                                                 @Param("month") int month);
}
