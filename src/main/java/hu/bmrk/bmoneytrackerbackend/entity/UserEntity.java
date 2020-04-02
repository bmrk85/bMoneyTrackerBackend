package hu.bmrk.bmoneytrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "userEntity")
public class UserEntity {


    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    @JsonManagedReference("user-spendings")
    @OneToMany(mappedBy = "userEntity")
    private List<Spending> spendings;

    @JsonManagedReference("user-savings")
    @OneToMany(mappedBy = "userEntity")
    private List<Saving> savings;

    @JsonManagedReference("user-incomes")
    @OneToMany(mappedBy = "userEntity")
    private List<Income> incomes;

    @JsonManagedReference("user-categories")
    @OneToMany(mappedBy = "userEntity")
    private List<Category> categories;






}
