package hu.bmrk.bmoneytrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String description;

    private boolean done;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date dateTo;

    private int amount;

    @JsonBackReference("category-savings")
    @ManyToOne
    private Category category;

    @JsonBackReference("user-savings")
    @ManyToOne
    private UserEntity userEntity;



}
