package pl.lodz.p.it.ssbd2024.ssbd01.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.ssbd01.config.ConfigurationProperties;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.Account;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.CredentialReset;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.PasswordHistory;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mok.TokenExpiredException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mok.TokenNotFoundException;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.repository.AccountMokRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.repository.CredentialResetRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.repository.GenericChangeCredentialTokenRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.repository.PasswordHistoryRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.util.messages.ExceptionMessages;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ServiceVerifier {
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final AccountMokRepository accountMokRepository;
    private final PasswordEncoder passwordEncoder;
    private final CredentialResetRepository resetMyCredentialRepository;
    private final ConfigurationProperties config;

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {Exception.class})
    public boolean isPasswordInHistory(UUID accountId, String password) {
        return passwordHistoryRepository.findPasswordHistoryByAccount_Id(accountId)
                .stream().anyMatch(passwordHistory -> passwordEncoder.matches(password, passwordHistory.getPassword()));
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {Exception.class})
    public CredentialReset saveTokenToResetCredential(Account account) {
        var randString = RandomStringUtils.random(128, 0, 0, true, true, null, new SecureRandom());
        var expiration = config.getCredentialChangeTokenExpiration();
        var expirationDate = LocalDateTime.now().plusMinutes(expiration);
        var newResetIssue = new CredentialReset(randString, account, expirationDate);
        resetMyCredentialRepository.saveAndFlush(newResetIssue);
        return newResetIssue;
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {Exception.class})
    public <T extends AbstractCredentialChange> Account verifyCredentialReset(String token, GenericChangeCredentialTokenRepository<T> repo)
            throws AccountNotFoundException, TokenNotFoundException, TokenExpiredException {
        Optional<T> credentialReset = repo.findByToken(token);
        if (credentialReset.isEmpty()) {
            throw new TokenNotFoundException(ExceptionMessages.TOKEN_NOT_FOUND);
        }
        if (credentialReset.get().getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(ExceptionMessages.TOKEN_EXPIRED);
        }
        return accountMokRepository.findByEmail(credentialReset.get().getAccount().getEmail())
                .orElseThrow(() -> new AccountNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
    }
}