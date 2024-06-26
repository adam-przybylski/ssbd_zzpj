package pl.lodz.p.it.ssbd2024.ssbd01.util;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.lodz.p.it.ssbd2024.ssbd01.config.ConfigurationProperties;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.Account;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractCredentialChange extends ControlledEntity {
    @Column(nullable = false, unique = true)
    @NotNull
    private  String token;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false, unique = true)
    @NotNull
    private Account account;

    @Column(nullable = false)
    @NotNull
    @Future
    private LocalDateTime expirationDate;


    public AbstractCredentialChange(Account account, LocalDateTime expirationDate) {
        this.token = TokenGenerator.generateToken();
        this.account = account;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractCredentialChange that = (AbstractCredentialChange) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
