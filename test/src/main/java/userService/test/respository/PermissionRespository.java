package userService.test.respository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.test.entity.Permission;

@Repository
public interface PermissionRespository extends JpaRepository<Permission, String> {}
