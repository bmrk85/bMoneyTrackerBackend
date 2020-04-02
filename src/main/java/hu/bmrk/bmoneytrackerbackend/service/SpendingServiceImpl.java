package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.SpendingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.repository.SpendingRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SpendingService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpendingServiceImpl implements SpendingService {

    @Autowired
    SpendingRepository spendingRepository;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    HelperUtil helper;

    @Override
    public SpendingDTO findFirstByIdAndUserEntity_Id(Long id, Long userId) {
        return helper.map(spendingRepository.findFirstByIdAndUserEntity_Id(id, userId), SpendingDTO.class);
    }

    @Override
    public List<SpendingDTO> findAllByUserEntity_IdOrderByDateFromAsc(Long userId) {
        return helper.mapAll(spendingRepository.findAllByUserEntity_IdOrderByDateAsc(userId), SpendingDTO.class);
    }

    @Override
    public List<SpendingDTO> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId) {
        return helper.mapAll(spendingRepository.findAllByDateBetweenAndUserEntity_Id(timeFrom, timeTo, userId), SpendingDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        spendingRepository.deleteById(id);
    }

    @Override
    public SpendingDTO save(SpendingDTO spendingDTO, UserEntity user){
        Spending spending = helper.map(spendingDTO, Spending.class);
        spending.setUserEntity(user);
        if(spending.getCategory().getId() == null){
            spending.getCategory().setUserEntity(user);
            spending.setCategory(categoryService.saveCategory(helper.map(spending.getCategory(), Category.class)));
        }

        return helper.map(spendingRepository.save(spending), SpendingDTO.class);
    }


}
