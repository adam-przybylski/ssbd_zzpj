package pl.lodz.p.it.ssbd2024.ssbd01.entity.mok;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2024.ssbd01.util.AbstractCredentialChange;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "change_my_email")
public class ChangeMyEmail extends AbstractCredentialChange {

    @Column(nullable = false)
    @Email
    private String email;

    public ChangeMyEmail(String token, Account account, LocalDateTime expirationDate, String email) {
        super(token, account, expirationDate);
        this.email = email;
    }
}
