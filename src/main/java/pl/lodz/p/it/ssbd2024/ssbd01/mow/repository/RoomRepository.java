package pl.lodz.p.it.ssbd2024.ssbd01.mow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mow.Room;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("hasRole('ROLE_MANAGER')")
public interface RoomRepository extends JpaRepository<Room, UUID> {

    @PreAuthorize("permitAll()")
    Page<Room> findAllByLocationIdAndIsActiveTrue(UUID locationId, Pageable pageUtils);

    List<Room> findAllByLocationIdAndIsActiveTrue(UUID locationId);

    List<Room> findAllByLocationIdAndIsActiveFalse(UUID locationId);

    @PreAuthorize("permitAll()")
    Optional<Room> findByIdAndIsActiveTrue(UUID id);

    Optional<Room> findByIdAndIsActiveFalse(UUID id);
}
