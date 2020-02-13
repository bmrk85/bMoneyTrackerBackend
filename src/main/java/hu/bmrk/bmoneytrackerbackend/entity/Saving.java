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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="savings")
public class Saving {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Date dateFrom;

    private Date dateTo;

    private int amount;

    @JsonBackReference
    @ManyToOne
    private Users users;



}
