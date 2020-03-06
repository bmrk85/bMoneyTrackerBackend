package hu.bmrk.bmoneytrackerbackend.entity.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IncomeDTO {

    private Long id;

    private String name;

    @JsonBackReference("category-incomes")
    private CategoryDTO category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private int amount;

    @JsonBackReference("user-incomes")
    private UserEntityDTO userEntity;

}
