package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving,Long> {

    Optional<Saving> findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Saving> findAllByUserEntity_IdOrderByDateFromAsc(Long userId);

    List<Saving> findAllByCategory_Title(String title);


}
