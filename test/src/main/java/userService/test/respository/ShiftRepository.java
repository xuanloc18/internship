package userService.test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.test.entity.Shift;

import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    @Query("SELECT a FROM Shift a WHERE a.shiftName =:shiftName")
    Optional<Shift> findShiftByName (@Param("shiftName") String shiftName);
}
