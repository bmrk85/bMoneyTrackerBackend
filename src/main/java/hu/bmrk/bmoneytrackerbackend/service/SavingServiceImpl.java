package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.Saving;
import hu.bmrk.bmoneytrackerbackend.repository.SavingRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
    SavingRepository savingRepository;


    @Override
    public Saving findFirstByIdAndUserEntity_Id(Long id, Long userId) {
        return savingRepository.findFirstByIdAndUserEntity_Id(id, userId);
    }

    @Override
    public List<Saving> findAllByUserEntity_Id(Long userId) {
        return savingRepository.findAllByUserEntity_IdOrderByDateFromAsc(userId);
    }

    @Override
    public List<Saving> findAllByCategory_Title(String title) {
        return savingRepository.findAllByCategory_Title(title);
    }

    @Override
    public void deleteSavingById(Long id) {
        savingRepository.deleteById(id);
    }

    @Override
    public Saving saveSaving(Saving s) {
        return savingRepository.save(s);
    }
}
