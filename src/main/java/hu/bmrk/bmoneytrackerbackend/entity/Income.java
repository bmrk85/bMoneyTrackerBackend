package hu.bmrk.bmoneytrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="incomes")
public class Income {

    @Id
    @GeneratedValue
    private Long id;

    private String category;

    private Date date;

    private int amount;

    @JsonBackReference
    @ManyToOne
    private Users users;


}
