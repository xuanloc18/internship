package userService.test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.test.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
