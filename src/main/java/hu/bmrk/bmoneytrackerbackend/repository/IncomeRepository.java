package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income,Long> {

    Income findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Income> findAllByUserEntity_Id(Long userId);

    List<Income> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId);

    void deleteIncomeById(Long id);


}
