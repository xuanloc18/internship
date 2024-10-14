package userService.test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import userService.test.entity.UserShift;

import java.util.List;

@Repository
public interface UserShiftRepository extends JpaRepository<UserShift, Integer>
{

}
