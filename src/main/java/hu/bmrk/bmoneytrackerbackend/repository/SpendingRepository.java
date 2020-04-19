package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long> {

    Optional<Spending> findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Spending> findAllByUserEntity_IdOrderByDateAsc(Long userId);

    List<Spending> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId); //todo: not working

    List<Spending> findAllByCategory_Title(String title);

}
