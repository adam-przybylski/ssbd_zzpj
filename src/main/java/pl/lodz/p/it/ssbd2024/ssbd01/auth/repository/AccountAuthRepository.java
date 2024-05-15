package pl.lodz.p.it.ssbd2024.ssbd01.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.Account;

import java.util.UUID;


@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface AccountAuthRepository extends JpaRepository<Account, UUID> {

    @PreAuthorize("permitAll()")
    Account findByUsername(String username);

    @PreAuthorize("permitAll()")
    @Override
    Account saveAndFlush(Account account);
}
