package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income,Long> {

    Optional<Income> findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Income> findAllByUserEntity_IdOrderByDateAsc(Long userId);

    List<Income> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId);

    List<Income> findAllByCategory_Title(String title);



}
