package hu.bmrk.bmoneytrackerbackend.entity.DTO;


import hu.bmrk.bmoneytrackerbackend.entity.Category;
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

    private CategoryDTO category;

    private UserEntityDTO userEntity;

}
