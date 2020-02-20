package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.Income;
import hu.bmrk.bmoneytrackerbackend.repository.IncomeRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    @Override
    public Income findFirstByIdAndUserEntity_Id(Long id, Long userId) {
        return incomeRepository.findFirstByIdAndUserEntity_Id(id, userId);
    }

    @Override
    public List<Income> findAllByUserEntity_Id(Long userId) {
        return incomeRepository.findAllByUserEntity_Id(userId);
    }

    @Override
    public List<Income> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId) {
        return incomeRepository.findAllByDateBetweenAndUserEntity_Id(timeFrom, timeTo, userId);
    }

    @Override
    public void deleteIncomeById(Long id) {
        incomeRepository.deleteById(id);
    }

    @Override
    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }
}
