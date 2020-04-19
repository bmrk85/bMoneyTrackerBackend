package hu.bmrk.bmoneytrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="incomes")
@Builder
public class Income {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnoreProperties(value = {"incomes", "savings", "spendings"}, allowSetters = true)
    @ManyToOne
    private Category category;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;

    private int amount;

    @JsonBackReference("user-incomes")
    @ManyToOne
    private UserEntity userEntity;


}
