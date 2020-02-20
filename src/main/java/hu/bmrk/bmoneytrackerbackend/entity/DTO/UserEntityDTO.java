package hu.bmrk.bmoneytrackerbackend.entity.DTO;

import hu.bmrk.bmoneytrackerbackend.entity.Income;
import hu.bmrk.bmoneytrackerbackend.entity.Saving;
import hu.bmrk.bmoneytrackerbackend.entity.Spending;
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

    private String uname;

    private List<Spending> spendings;

    private List<Saving> savings;

    private List<Income> incomes;

}
