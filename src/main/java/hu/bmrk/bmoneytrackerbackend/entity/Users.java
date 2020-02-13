package hu.bmrk.bmoneytrackerbackend.entity;

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
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue
    private Long id;

    private String uname;

    private String password;//todo titkosítás

    @OneToMany(mappedBy = "users")
    private List<Spending> spendings;

    @OneToMany(mappedBy = "users")
    private List<Saving> savings;

    @OneToMany(mappedBy = "users")
    private List<Income> incomes;






}
