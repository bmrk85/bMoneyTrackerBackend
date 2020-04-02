package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.UserEntityDTO;

import java.util.List;

public interface UserEntityService {

    UserEntityDTO findFirstById(Long id);

    List<UserEntityDTO> findAll();

    UserEntityDTO findByUsername(String username);



}
