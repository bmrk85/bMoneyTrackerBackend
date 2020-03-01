package hu.bmrk.bmoneytrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="categories")
public class Category {

    @Id
    private String title;

    @JsonManagedReference("category-incomes")
    @OneToMany(mappedBy = "category")
    private List<Income> incomes;

    @JsonManagedReference("category-savings")
    @OneToMany(mappedBy = "category")
    private List<Saving> savings;

    @JsonManagedReference("category-spendings")
    @OneToMany(mappedBy = "category")
    private List<Spending> spendings;
}
