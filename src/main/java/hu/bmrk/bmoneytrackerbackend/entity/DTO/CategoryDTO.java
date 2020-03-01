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
public class CategoryDTO {

    private String title;

    private List<IncomeDTO> incomes;

    private List<SavingDTO> savings;

    private List<SpendingDTO> spendings;
}
