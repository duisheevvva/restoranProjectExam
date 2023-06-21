package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;


@Entity
@Table(name = "stop_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StopList {
    @Id
    @GeneratedValue(generator = "stopList_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "stopList_gen",
            sequenceName = "stopList_seq",
            allocationSize = 1)
    private Long id;
    @NotEmpty(message = "fill in the field")
    private String reason;
    private LocalDate date;


    @OneToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.REMOVE}, mappedBy = "stopList")
    private MenuItem menuItem;

    public StopList(String reason, LocalDate date) {
        this.reason = reason;
        this.date = date;
    }
}
