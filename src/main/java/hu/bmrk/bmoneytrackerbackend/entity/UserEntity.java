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
@Table(name = "userEntity")
public class UserEntity {


    public UserEntity(String uname, String password) {
        this.uname = uname;
        this.password = password;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String uname;

    private String password;//todo titkosítás

    @OneToMany(mappedBy = "userEntity")
    private List<Spending> spendings;

    @OneToMany(mappedBy = "userEntity")
    private List<Saving> savings;

    @OneToMany(mappedBy = "userEntity")
    private List<Income> incomes;






}
