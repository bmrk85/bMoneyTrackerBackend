package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;

import java.util.Date;
import java.util.List;

public interface BaseService<DTOType> {

    DTOType findFirstByIdAndUserEntity_Id(Long id, Long userId);

    List<DTOType> findAllByUserEntity_IdOrderByDateFromAsc(Long userId);

    List<DTOType> findAllByDateBetweenAndUserEntity_Id(Date timeFrom, Date timeTo, Long userId);

    DTOType save(DTOType entityDTO, UserEntity userEntity);

    void deleteById(Long id);

}
