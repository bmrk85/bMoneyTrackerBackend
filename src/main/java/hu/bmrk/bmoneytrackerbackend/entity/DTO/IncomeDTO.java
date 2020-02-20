package hu.bmrk.bmoneytrackerbackend.entity.DTO;

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

    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private int amount;

    private UserEntityDTO userEntity;

}
