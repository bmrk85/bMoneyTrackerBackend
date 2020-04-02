package hu.bmrk.bmoneytrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private boolean enabled;

    private String  color;

    @JsonBackReference("user-categories")
    @ManyToOne
    private UserEntity userEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Income> incomes;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Saving> savings;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Spending> spendings;



}
