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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SavingDTO {

    private Long id;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTo;

    private int amount;

    @JsonBackReference("category-savings")
    private CategoryDTO category;

    @JsonBackReference("user-savings")
    private UserEntityDTO userEntity;

}
