package hu.bmrk.bmoneytrackerbackend;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.repository.UserEntityRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserEntityService userService;

    @Autowired
    private UserEntityRepository userEntityRepository;

    private String userName;

    @BeforeEach
    void init(){
        UserEntity user = userEntityRepository.save(UserEntity.builder()
                .username("Test Test")
                .password("asdasd")
                .registerDate(new Date())
                .incomes(null)
                .spendings(null)
                .categories(null)
                .build()
        );
        userName = user.getUsername();
    }

    @AfterEach
    void clean(){
        userEntityRepository.deleteAll();
    }

    @Test
    @Transactional
    void getUsers(){
        assertEquals(1, userService.findAll().size());
    }


    @Test
    @Transactional
    void getByUname(){
        assertNotNull(userService.findByUsername(userName));
    }

    @Test
    void unsuccessfulGetByUname(){
        assertThrows(EntityNotFoundException.class, () -> userService.findByUsername("not existing"));
    }



}
