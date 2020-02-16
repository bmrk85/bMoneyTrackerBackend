package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findFirstById(Long id);

    UserEntity findByUname(String uname);


}
