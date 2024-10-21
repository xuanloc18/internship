package userService.test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.test.entity.LeaveRequest;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user.userName = :userName")
    List<LeaveRequest> finAllOfMy(@Param("userName") String userName);
}
