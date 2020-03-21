package hu.bmrk.bmoneytrackerbackend.entity.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntityDTO {

    private Long id;

    private String username;

    @JsonManagedReference("user-spendings")
    private List<SpendingDTO> spendings;

    @JsonManagedReference("user-savings")
    private List<SavingDTO> savings;

    @JsonManagedReference("user-incomes")
    private List<IncomeDTO> incomes;

    @JsonManagedReference("user-categories")
    private List<CategoryDTO> categories;

}
