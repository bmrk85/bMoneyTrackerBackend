package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.Income;

import java.sql.Timestamp;
import java.util.List;

public interface IncomeService {

    Income findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Income> findAllByUserEntity_Id(Long userId);

    List<Income> findAllByDateBetweenAndUserEntity_Id(Timestamp timeFrom, Timestamp timeTo, Long userId);

    List<Income> findAllByCategory_Title(String title);

    void deleteIncomeById(Long id);

    Income saveIncome(Income income);


}
