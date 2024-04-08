package pl.lodz.p.it.ssbd2024.ssbd01.mok.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.ssbd01.auth.request.RegisterUserRequest;
import pl.lodz.p.it.ssbd2024.ssbd01.entities.mok.Account;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.converters.AccountToAccountDto;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2024.ssbd01.mok.services.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        List<AccountDto> accountDtos = AccountToAccountDto.accountDtoList(accountService.getAllUsers());
        return ResponseEntity.status(HttpStatus.OK).body(accountDtos).getBody();
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody RegisterUserRequest request) {
        Account account = new Account(request.getUsername(), request.getPassword(), request.getEmail(), request.getGender(),
                request.getFirstName(), request.getLastName());
        AccountDto accountDto = AccountToAccountDto.toAccountDto(accountService.addUser(account));
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @PostMapping("/{id}/addRole")
    public ResponseEntity<AccountDto> addRoleToAccount(@PathVariable UUID id,
                                                       @RequestParam String roleName) {
        AccountDto updatedAccount = AccountToAccountDto
                .toAccountDto(accountService.addRoleToAccount(id, roleName));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    @DeleteMapping("/{id}/takeRole")
    public ResponseEntity<AccountDto> takeRoleOffAccount(@PathVariable UUID id,
                                                         @RequestParam String roleName) {
        AccountDto updatedAccount = AccountToAccountDto
                .toAccountDto(accountService.takeRole(id, roleName));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }
}
