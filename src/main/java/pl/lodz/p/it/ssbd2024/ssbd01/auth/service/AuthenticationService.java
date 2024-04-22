package pl.lodz.p.it.ssbd2024.ssbd01.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssbd2024.ssbd01.auth.repository.AccountAuthRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.config.security.JwtService;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.LoginDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.create.CreateAccountDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.converter.AccountDTOConverter;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.service.AccountService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountService accountService;
    private final AccountAuthRepository accountAuthRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountDTOConverter accountDTOConverter;

    public String registerUser(CreateAccountDTO createAccountDTO) {
        return jwtService.generateToken(
                accountService.addAccount(
                        accountDTOConverter.toAccount(
                                createAccountDTO)));
    }

    @Transactional
    public String authenticate(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.username(),
                        loginDTO.password()
                )
        );
        var user = accountAuthRepository.findByUsername(loginDTO.username());
        return jwtService.generateToken(user);
    }

}
