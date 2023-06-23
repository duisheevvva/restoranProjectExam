package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "cheques")
@Getter
@Setter
@NoArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(generator = "cheque_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_gen",
            sequenceName = "cheque_seq",
            allocationSize = 1)
    private Long id;
    private int priceAverage;
    private LocalDate createdAt;
    private int grandTotal;


    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private User user;


    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH}, mappedBy = "chequeList")
    private List<MenuItem> menuItems;


    public Cheque(Long id, int priceAverage, LocalDate createdAt, int grandTotal) {
        this.id = id;
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
        this.grandTotal = grandTotal;
    }

    public void setUsers(User authentication) {
    }
}

