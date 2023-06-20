package peaksoft.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subcategory")
@Getter
@Setter
@NoArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(generator = "subcategory_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "subcategory_gen",
            sequenceName = "subcategory_seq",
            allocationSize = 1)
    private Long id;
    @NotEmpty(message = "fill in the field")
    private String name;


    @OneToMany(mappedBy = "subcategory", cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private List<MenuItem> menuItems;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Category category;

    public Subcategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addMenuItem(MenuItem menuItem) {
    }
}
