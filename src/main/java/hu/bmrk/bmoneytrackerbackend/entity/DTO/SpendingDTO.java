package hu.bmrk.bmoneytrackerbackend.entity.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SpendingDTO {

    private Long id;

    private String name;

    @JsonBackReference("category-spendings")
    private CategoryDTO category;

    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date date;

    @JsonBackReference("user-spendings")
    private UserEntityDTO userEntity;

}
