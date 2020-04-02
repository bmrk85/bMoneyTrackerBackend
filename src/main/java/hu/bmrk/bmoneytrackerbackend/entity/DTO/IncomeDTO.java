package hu.bmrk.bmoneytrackerbackend.entity.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonIgnoreProperties(value = {"incomes", "savings", "spendings"}, allowSetters = true)
    private CategoryDTO category;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;

    private int amount;

    @JsonBackReference("user-incomes")
    private UserEntityDTO userEntity;

}
