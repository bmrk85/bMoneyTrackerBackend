package hu.bmrk.bmoneytrackerbackend.entity.DTO;

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

    private List<SpendingDTO> spendings;

    private List<SavingDTO> savings;

    private List<IncomeDTO> incomes;

}
