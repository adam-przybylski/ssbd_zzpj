package pl.lodz.p.it.ssbd2024.ssbd01.entity.mok;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2024.ssbd01.util.AbstractCredentialChange;
import pl.lodz.p.it.ssbd2024.ssbd01.util.AbstractEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "change_my_password")
public class ChangeMyPassword extends AbstractCredentialChange {

    @Column(nullable = false)
    @Size(min = 8, max = 72)
    private String password;

    public ChangeMyPassword(String token, Account account, LocalDateTime expirationDate, String password) {
        super(token, account, expirationDate);
        this.password = password;
    }

}
