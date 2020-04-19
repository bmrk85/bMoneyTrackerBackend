package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.UserEntityDTO;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.repository.UserEntityRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    HelperUtil helper;

    @Override
    public UserEntityDTO findFirstById(Long id) {
        return helper.map(userEntityRepository.findFirstById(id),UserEntityDTO.class);
    }

    @Override
    public List<UserEntityDTO> findAll() {
        return helper.mapAll(userEntityRepository.findAll(), UserEntityDTO.class);
    }

    @Override
    public UserEntityDTO findByUsername(String username) {
        UserEntity user = userEntityRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        return helper.map(user, UserEntityDTO.class);
    }
}
