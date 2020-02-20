package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long> {

    Spending findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Spending> findAllByUserEntity_Id(Long userId);

    List<Spending> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId);

    void deleteSpendingById(Long id);

}
