package pl.lodz.p.it.ssbd2024.ssbd01.mok.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.create.CreateAccountDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.get.GetAccountDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.update.UpdateAccountDataDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.update.UpdateEmailDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.Account;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.abstract_exception.BadRequestException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.abstract_exception.ConflictException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.abstract_exception.NotFoundException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.abstract_exception.UnprocessableEntityException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mok.EmailAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.converter.AccountDTOConverter;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.service.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final AccountDTOConverter AccountDTOConverter;

    @GetMapping
    public List<GetAccountDTO> getAllUsers() {
        List<GetAccountDTO> getAccountDTOS = AccountDTOConverter.accountDtoList(accountService.getAllAccounts());
        return ResponseEntity.status(HttpStatus.OK).body(getAccountDTOS).getBody();
    }

    @PostMapping
    public ResponseEntity<GetAccountDTO> createUser(@RequestBody CreateAccountDTO createAccountDTO) {
        GetAccountDTO getAccountDTO = AccountDTOConverter.toAccountDto(accountService.addAccount(AccountDTOConverter.toAccount(createAccountDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(getAccountDTO);
    }

    @PostMapping("/{id}/addRole")
    public ResponseEntity<GetAccountDTO> addRoleToAccount(@PathVariable UUID id, @RequestParam String roleName)
            throws BadRequestException, UnprocessableEntityException, NotFoundException, ConflictException {
        GetAccountDTO updatedAccount = AccountDTOConverter.toAccountDto(accountService.addRoleToAccount(id, roleName));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    @DeleteMapping("/{id}/removeRole")
    public ResponseEntity<GetAccountDTO> removeRole(@PathVariable UUID id, @RequestParam String roleName)
            throws BadRequestException, NotFoundException {
        GetAccountDTO updatedAccount = AccountDTOConverter.toAccountDto(accountService.removeRole(id, roleName));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    @PatchMapping("/{id}/setActive")
    public ResponseEntity<GetAccountDTO> setActive(@PathVariable UUID id) throws NotFoundException {
        GetAccountDTO updatedAccount = AccountDTOConverter.toAccountDto(accountService.setAccountStatus(id, true));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    @PatchMapping("/{id}/setInactive")
    public ResponseEntity<GetAccountDTO> setInactive(@PathVariable UUID id) throws NotFoundException {
        GetAccountDTO updatedAccount = AccountDTOConverter.toAccountDto(accountService.setAccountStatus(id, false));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<GetAccountDTO> getAccountByUsername(@PathVariable String username) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            if (userDetails.getUsername().equals(username)) {
                GetAccountDTO accountDto = AccountDTOConverter.toAccountDto(accountService.getAccountByUsername(username));
                return ResponseEntity.status(HttpStatus.OK).body(accountDto);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @PutMapping("/userData/{id}")
    public ResponseEntity<GetAccountDTO> updateAccountData(@PathVariable UUID id, @RequestBody UpdateAccountDataDTO updateAccountDataDTO)
            throws NotFoundException {
        GetAccountDTO updatedAccount =
                AccountDTOConverter.toAccountDto(accountService.updateAccountData(id, AccountDTOConverter.toAccount(updateAccountDataDTO)));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    @GetMapping("/participants")
    public ResponseEntity<List<GetAccountDTO>> getParticipants() throws NotFoundException {
        List<GetAccountDTO> participants = AccountDTOConverter.accountDtoList(accountService.getParticipants());
        return ResponseEntity.status(HttpStatus.OK).body(participants);
    }

    @GetMapping("/administrators")
    public ResponseEntity<List<GetAccountDTO>> getAdministrators() throws NotFoundException {
        List<GetAccountDTO> admiministrators = AccountDTOConverter.accountDtoList(accountService.getAdmins());
        return ResponseEntity.status(HttpStatus.OK).body(admiministrators);
    }

    @GetMapping("/managers")
    public ResponseEntity<List<GetAccountDTO>> getManagers() throws NotFoundException {
        List<GetAccountDTO> managers = AccountDTOConverter.accountDtoList(accountService.getManagers());
        return ResponseEntity.status(HttpStatus.OK).body(managers);
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<GetAccountDTO> updateAccountEmail(@PathVariable UUID id, @RequestBody UpdateEmailDTO email)
            throws AccountNotFoundException, EmailAlreadyExistsException {
        GetAccountDTO updatedAccount = AccountDTOConverter
                .toAccountDto(accountService.updateAccountEmail(id, email.email()));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }
}