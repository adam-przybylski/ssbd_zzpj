package pl.lodz.p.it.ssbd2024.ssbd01.entity.mow;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2024.ssbd01.util.ControlledEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "event")
public class Event extends ControlledEntity {

    @Column(nullable = false, unique = true, updatable = false)
    @NotBlank
    @Size(min = 3, max = 128)
    private String name;

    @Size(min = 3, max = 1024)
    @NotBlank
    @Column(columnDefinition = "varchar(1024)")
    private String description;

    @Column(nullable = false)
    @NotNull
    private Boolean isNotCanceled = true;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "event")
    private List<Session> sessions = new ArrayList<>();

    // TODO: Move to other table or remove
    @PositiveOrZero
    private long counter = 0;

    /**
     * Sessions that are part of event cannot start before start date.
     */
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    /**
     * Sessions that are part of event cannot start after end date.
     */
    @NotNull
    @FutureOrPresent
    private LocalDate endDate;

    public Event(String name, String description, List<Session> sessions,
                 LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.sessions = sessions;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event event)) {
            return false;
        }
        return Objects.equals(name, event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
