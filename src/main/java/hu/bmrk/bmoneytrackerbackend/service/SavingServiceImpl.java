package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.SavingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Saving;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.repository.SavingRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SavingService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
    SavingRepository savingRepository;

    @Autowired
    HelperUtil helper;

    @Autowired
    CategoryService categoryService;


    @Override
    public SavingDTO findFirstByIdAndUserEntity_Id(Long id, Long userId) {
        return helper.map(savingRepository.findFirstByIdAndUserEntity_Id(id, userId), SavingDTO.class);
    }

    @Override
    public List<SavingDTO> findAllByUserEntity_IdOrderByDateFromAsc(Long userId) {
        return helper.mapAll(savingRepository.findAllByUserEntity_IdOrderByDateFromAsc(userId), SavingDTO.class);
    }

    @Override
    public List<SavingDTO> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteById(Long id) {
        savingRepository.deleteById(id);
    }

    @Override
    public SavingDTO save(SavingDTO savingDTO, UserEntity user) {

        Saving saving = helper.map(savingDTO, Saving.class);
        saving.setUserEntity(user);
        if(saving.getCategory().getId() == null){
            saving.getCategory().setUserEntity(user);
            saving.setCategory(categoryService.saveCategory(helper.map(saving.getCategory(), Category.class)));
        }

        return helper.map(savingRepository.save(saving), SavingDTO.class);
    }
}
