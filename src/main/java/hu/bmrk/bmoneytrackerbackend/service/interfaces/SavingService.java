package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.Saving;

import java.util.List;

public interface SavingService {

    Saving findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<Saving> findAllByUserEntity_Id(Long userId);

    List<Saving> findAllByCategory_Title(String title);

    void deleteSavingById(Long id);

    Saving saveSaving(Saving s);
}
