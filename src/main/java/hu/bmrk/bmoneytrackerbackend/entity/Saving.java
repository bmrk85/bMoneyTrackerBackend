package hu.bmrk.bmoneytrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="savings")
@Builder
public class Saving {

    @Id
    @GeneratedValue
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
    @ManyToOne
    private Category category;

    @JsonBackReference("user-savings")
    @ManyToOne
    private UserEntity userEntity;



}
