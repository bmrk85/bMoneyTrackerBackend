package hu.bmrk.bmoneytrackerbackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="spendings")
public class Spending {


    @Id
    @GeneratedValue
    private Long id;

    private String category;

    private int amount;

    private Date date;

    @JsonBackReference
    @ManyToOne
    private UserEntity userEntity;



}
