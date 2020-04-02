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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SavingDTO {

    private Long id;

    private String name;

    private String description;

    private boolean done;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date dateTo;

    private int amount;

    @JsonIgnoreProperties(value = {"incomes", "savings", "spendings"}, allowSetters = true)
    private CategoryDTO category;

    @JsonBackReference("user-savings")
    private UserEntityDTO userEntity;

}
