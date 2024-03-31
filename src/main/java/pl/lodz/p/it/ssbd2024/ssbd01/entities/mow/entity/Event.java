package pl.lodz.p.it.ssbd2024.ssbd01.entities.mow.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.lodz.p.it.ssbd2024.ssbd01.entities.util.AbstractEntity;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Event extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Session> sessions;
}
