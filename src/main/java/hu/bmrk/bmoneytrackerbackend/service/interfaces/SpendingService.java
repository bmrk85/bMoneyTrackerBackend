package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.Spending;

import java.util.Date;
import java.util.List;

public interface SpendingService {

    Spending findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Spending> findAllByUserEntity_Id(Long userId);

    List<Spending> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId);

    List<Spending> findAllByCategory_Title(String title);

    void deleteSpendingById(Long id);

    Spending saveSpending(Spending s);

}
