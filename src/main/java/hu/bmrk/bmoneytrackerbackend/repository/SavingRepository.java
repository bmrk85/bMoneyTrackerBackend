package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingRepository extends JpaRepository<Saving,Long> {

    Saving findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Saving> findAllByUserEntity_IdOrderByDateFromAsc(Long userId);

    List<Saving> findAllByCategory_Title(String title);


}
