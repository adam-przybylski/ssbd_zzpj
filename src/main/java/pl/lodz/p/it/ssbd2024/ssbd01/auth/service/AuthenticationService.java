package pl.lodz.p.it.ssbd2024.ssbd01.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.ssbd01.auth.repository.AccountAuthRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.config.security.JwtService;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.LoginDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.Account;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.AccountConfirmation;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.auth.AccountConfirmationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.auth.AccountConfirmationTokenNotFoundException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2024.ssbd01.messages.ExceptionMessages;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.repository.AccountConfirmationRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.repository.AccountMokRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:expiration.properties")
public class AuthenticationService {
    private final AccountMokRepository accountMokRepository;
    private final AccountAuthRepository accountAuthRepository;
    private final AccountConfirmationRepository accountConfirmationRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    @Value("${auth.attempts:3}")
    private int maxFailedLoginAttempts;

    @Value("${auth.lock-time:86400}")
    private int lockTimeout;

    private LocalDateTime calculateExpirationDate(int expirationHours) {
        return LocalDateTime.now().plusHours(expirationHours);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public void registerUser(Account account) {
        accountMokRepository.saveAndFlush(account);
        var randString = RandomStringUtils.random(128, 0, 0, true, true, null, new SecureRandom());
        var expirationHours = Integer.parseInt(Objects.requireNonNull(env.getProperty("confirmation.token.expiration.hours")));
        var expirationDate = calculateExpirationDate(expirationHours);
        var newAccountConfirmation = new AccountConfirmation(randString, account, expirationDate);
        // TODO: Send mail to user with confirmation link

        accountConfirmationRepository.saveAndFlush(newAccountConfirmation);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class}, noRollbackFor = {BadCredentialsException.class})
    public String authenticate(LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.username(),
                            loginDTO.password()
                    )
            );
        } catch (BadCredentialsException e) {
            updateFailedLoginAttempts(loginDTO.username());
            throw e;
        }

        var user = accountAuthRepository.findByUsername(loginDTO.username());
        user.setFailedLoginAttempts(0);
        accountAuthRepository.save(user);
        return jwtService.generateToken(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public void verifyAccount(String token)
            throws AccountConfirmationTokenNotFoundException, AccountConfirmationTokenExpiredException, AccountNotFoundException {
        var accountConfirmation = accountConfirmationRepository.findByToken(token)
                .orElseThrow(() -> new AccountConfirmationTokenNotFoundException(ExceptionMessages.CONFIRMATION_TOKEN_NOT_FOUND));
        if (accountConfirmation.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new AccountConfirmationTokenExpiredException(ExceptionMessages.CONFIRMATION_TOKEN_EXPIRED);
        }
        var accountId = accountConfirmation.getAccount().getId();
        var account = accountMokRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        account.setVerified(true);
        accountMokRepository.saveAndFlush(account);
        accountConfirmationRepository.delete(accountConfirmation);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Scheduled(fixedRate = 120000)
    public void deleteExpiredTokensAndAccounts() {
        LocalDateTime now = LocalDateTime.now();

        List<AccountConfirmation> expiredTokens = accountConfirmationRepository.findByExpirationDateBefore(now);

        for (AccountConfirmation token : expiredTokens) {
            Optional<Account> optionalAccount = accountMokRepository.findById(token.getAccount().getId());
            accountConfirmationRepository.delete(token);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                accountMokRepository.delete(account);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Scheduled(fixedRate = 120000)
    public void unlockAccounts() {
        LocalDateTime now = LocalDateTime.now();

        List<Account> lockedAccounts = accountMokRepository.findByNonLockedFalseAndLockedUntilBefore(now);

        for (Account account : lockedAccounts) {
            account.setNonLocked(true);
            account.setFailedLoginAttempts(0);
            account.setLockedUntil(null);
            accountMokRepository.save(account);
        }
    }

    public void updateFailedLoginAttempts(String username) {
        Account account = accountAuthRepository.findByUsername(username);
        account.setFailedLoginAttempts(account.getFailedLoginAttempts() + 1);
        if (account.getFailedLoginAttempts() >= maxFailedLoginAttempts) {
            account.setNonLocked(false);
            account.setLockedUntil(LocalDateTime.now().plusSeconds(lockTimeout));
        }
        accountAuthRepository.save(account);
    }

}
