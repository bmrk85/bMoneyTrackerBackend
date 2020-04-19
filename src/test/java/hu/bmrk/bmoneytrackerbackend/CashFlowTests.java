package hu.bmrk.bmoneytrackerbackend;

import hu.bmrk.bmoneytrackerbackend.entity.*;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.IncomeDTO;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.SavingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.SpendingDTO;
import hu.bmrk.bmoneytrackerbackend.repository.*;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.*;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CashFlowTests {

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    IncomeService incomeService;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SpendingRepository spendingRepository;

    @Autowired
    SavingRepository savingRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SpendingService spendingService;

    @Autowired
    HelperUtil helper;

    @Autowired
    CashFlowService cashFlowService;

    @Autowired
    SavingService savingService;

    private UserEntity user;

    private Long spendingId;
    private Long incomeId;
    private Long savingId;
    private Long categoryId;

    @BeforeEach
    void init() {

        UserEntity user = userEntityRepository.save(UserEntity.builder()
                .username("Test Test")
                .password("asdasd")
                .registerDate(new Date())
                .incomes(null)
                .spendings(null)
                .categories(null)
                .build()
        );
        this.user = user;

        Category category = categoryService.saveCategory(Category.builder()
                .color("test")
                .enabled(true)
                .title("test cat")
                .userEntity(user)
                .build());

        this.categoryId = category.getId();

        IncomeDTO income = incomeService.save(
                helper.map(Income.builder()
                        .userEntity(user)
                        .amount(1000)
                        .category(category)
                        .date(new Date())
                        .name("test income")
                        .build(), IncomeDTO.class), user);
        this.incomeId = income.getId();

        SpendingDTO spending = spendingService.save(
                helper.map(Spending.builder()
                        .userEntity(user)
                        .amount(1000)
                        .category(category)
                        .date(new Date())
                        .name("test spending")
                        .build(), SpendingDTO.class), user);
        this.spendingId = spending.getId();

        SavingDTO saving = savingService.save(
                helper.map(Saving.builder()
                        .userEntity(user)
                        .amount(20000)
                        .category(category)
                        .dateFrom(new Date())
                        .dateTo(new Date())
                        .description("test description")
                        .done(false)
                        .name("test saving").build(), SavingDTO.class), user);

        this.savingId = saving.getId();

    }

    @AfterEach
    void clean(){
        spendingRepository.deleteAll();
        incomeRepository.deleteAll();
        savingRepository.deleteAll();
        categoryRepository.deleteAll();
        userEntityRepository.deleteAll();
    }

    @Test
    @Transactional
    void getCashFlows() {
        assertEquals(cashFlowService.getCashFlowForUser(
                Date.from(LocalDate.now().minusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.now().plusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                user).size(), 2);
    }

    @Test
    @Transactional
    void getSpending() {
        assertNotNull(spendingService.findFirstByIdAndUserEntity_Id(spendingId, user.getId()));
    }

    @Test
    @Transactional
    void unsuccessfulGetSpending() {
        assertThrows(EntityNotFoundException.class, () -> spendingService.findFirstByIdAndUserEntity_Id(9999L, user.getId()));
    }

    @Test
    @Transactional
    void getIncome() {
        assertNotNull(incomeService.findFirstByIdAndUserEntity_Id(incomeId, user.getId()));
    }

    @Test
    @Transactional
    void unsuccessfulGetIncome() {
        assertThrows(EntityNotFoundException.class, () -> incomeService.findFirstByIdAndUserEntity_Id(9999L, user.getId()));
    }

    @Test
    @Transactional
    void getCategories() {
        assertNotNull(categoryService.getCategoriesForUser(user));
    }

    @Test
    @Transactional
    void getSaving() {
        assertNotNull(savingService.findFirstByIdAndUserEntity_Id(savingId, user.getId()));
    }

    @Test
    @Transactional
    void unsuccessfulGetSaving() {
        assertThrows(EntityNotFoundException.class, () -> savingService.findFirstByIdAndUserEntity_Id(9999L, user.getId()));
    }

    @Test
    void deleteIncome(){
        incomeService.deleteById(incomeId);
        assertEquals(0, incomeService.findAllByUserEntity_IdOrderByDateFromAsc(user.getId()).size());
    }

    @Test
    void deleteSaving(){
        savingService.deleteById(savingId);
        assertEquals(0, savingService.findAllByUserEntity_IdOrderByDateFromAsc(user.getId()).size());
    }

    @Test
    void deleteSpending(){
        spendingService.deleteById(spendingId);
        assertEquals(0, spendingService.findAllByUserEntity_IdOrderByDateFromAsc(user.getId()).size());
    }

    @Test
    void unsuccessfulDeleteCategory(){
        assertThrows(DataIntegrityViolationException.class, () -> categoryService.deleteById(categoryId));
    }

}
