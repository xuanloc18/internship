package userService.test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.test.entity.Departments;

import java.util.Optional;
@Repository
public interface DepartmentsRepository extends JpaRepository<Departments,Integer> {
//    String departments_name;
//    String departments_description;
    @Query("SELECT a FROM Departments a WHERE a.departments_name =:departments_name")
    Optional<Departments> findByDepartmentsname (@Param("departments_name") String departments_name);
}
