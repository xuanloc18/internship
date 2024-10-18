package userService.test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.test.entity.UserShift;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserShiftRepository extends JpaRepository<UserShift, Integer>
{

//    Long userID;
//    int shiftID;
//    LocalDate shift_date;
    @Query("SELECT a FROM UserShift a WHERE a.userID=:userID AND a.shiftID=:ShiftID AND a.shift_date=:shiftDate")
    Optional<UserShift> getUserShiftToCheck(@Param("userID") Long userID, @Param("ShiftID") int ShiftID, @Param("shiftDate") LocalDate shiftDate);
    @Query("SELECT a FROM UserShift a WHERE a.userID=:userID")
    List<UserShift> getUserShiftOfUser(@Param("userID") Long UserID);
}
