package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;

import java.util.List;

public interface UserEntityService {

    UserEntity findFirstById(Long id);

    List<UserEntity> findAll();



}
