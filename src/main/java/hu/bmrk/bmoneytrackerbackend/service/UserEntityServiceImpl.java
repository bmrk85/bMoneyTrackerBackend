package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.repository.UserEntityRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserEntity findFirstById(Long id) {
        return userEntityRepository.findFirstById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }
}
