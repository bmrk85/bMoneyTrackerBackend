package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import hu.bmrk.bmoneytrackerbackend.repository.SpendingRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SpendingServiceImpl implements SpendingService {

    @Autowired
    SpendingRepository spendingRepository;

    @Override
    public Spending findFirstByIdAndUserEntity_Id(Long id, Long userId) {
        return spendingRepository.findFirstByIdAndUserEntity_Id(id, userId);
    }

    @Override
    public List<Spending> findAllByUserEntity_Id(Long userId) {
        return spendingRepository.findAllByUserEntity_Id(userId);
    }

    @Override
    public List<Spending> findAllByDateBetweenAndUserEntity_Id(Timestamp timeFrom, Timestamp timeTo, Long userId) {
        return spendingRepository.findAllByDateBetweenAndUserEntity_Id(timeFrom, timeTo, userId);
    }

    @Override
    public List<Spending> findAllByCategory_Title(String title) {
        return spendingRepository.findAllByCategory_Title(title);
    }

    @Override
    public void deleteSpendingById(Long id) {
        spendingRepository.deleteById(id);
    }

    @Override
    public Spending saveSpending(Spending s){
        return spendingRepository.save(s);
    }



}
