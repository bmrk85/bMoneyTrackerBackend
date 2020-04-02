package hu.bmrk.bmoneytrackerbackend.entity.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {

    private Long id;

    private String title;

    private boolean enabled;

    private String color;


    @JsonBackReference("user-categories")
    private UserEntityDTO userEntity;

    @JsonIgnore
    private List<IncomeDTO> incomes;

    @JsonIgnore
    private List<SavingDTO> savings;

    @JsonIgnore
    private List<SpendingDTO> spendings;


}
