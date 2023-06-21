package peaksoft.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(generator = "menuItem_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menuItem_gen",
            sequenceName = "menuItem_seq",
            allocationSize = 1)
    private Long id;
    @NotEmpty(message = "fill in the field")
    private String name;
    @NotEmpty(message = "fill in the field")
    private String image;
    private int price;
    @NotEmpty(message = "fill in the field")
    private String description;
    private boolean isVegetarian;
    private LocalDate isBlocked;


    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Restaurant restaurant;

    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private List<Cheque> chequeList;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Restaurant restaurantM;


    @OneToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private StopList stopList;


    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Subcategory subcategory;

    public MenuItem(Long id, String name, String image, int price, String description, boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }


}
