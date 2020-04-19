package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.IncomeDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Income;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.repository.IncomeRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.IncomeService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HelperUtil helper;

    @Override
    public IncomeDTO findFirstByIdAndUserEntity_Id(Long id, Long userId) {
        return helper.map(incomeRepository.findFirstByIdAndUserEntity_Id(id, userId).orElseThrow(EntityNotFoundException::new), IncomeDTO.class);
    }

    @Override
    public List<IncomeDTO> findAllByUserEntity_IdOrderByDateFromAsc(Long userId) {
        incomeRepository.save(new Income());
        return helper.mapAll(incomeRepository.findAllByUserEntity_IdOrderByDateAsc(userId), IncomeDTO.class);
    }

    @Override
    public List<IncomeDTO> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId) {

        return helper.mapAll(incomeRepository.findAllByDateBetweenAndUserEntity_Id(timeFrom, timeTo, userId), IncomeDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        incomeRepository.deleteById(id);
    }

    @Override
    public IncomeDTO save(IncomeDTO incomeDTO, UserEntity user) {

        Income income = helper.map(incomeDTO, Income.class);
        income.setUserEntity(user);
        if(income.getCategory().getId() == null){
            income.getCategory().setUserEntity(user);
            income.setCategory(categoryService.saveCategory(helper.map(income.getCategory(), Category.class)));
        }

        return helper.map(incomeRepository.save(income), IncomeDTO.class);
    }
}
