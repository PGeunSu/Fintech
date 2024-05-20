package com.zerobase.Fintech.controller;

import com.zerobase.Fintech.dto.AccountCreateForm;
import com.zerobase.Fintech.dto.AccountDeleteForm;
import com.zerobase.Fintech.dto.AccountDto;
import com.zerobase.Fintech.repository.AccountRepository;
import com.zerobase.Fintech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

  private final AccountService accountService;
  private final AccountRepository accountRepository;

  @PostMapping("/create")
  private ResponseEntity<AccountDto> createAccount(
      @RequestBody AccountCreateForm form
  ){
    String holder = accountRepository.findByHolder(form.getHolder()).toString();

    return ResponseEntity.ok(accountService.createAccount(holder, form));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<AccountDto> deleteAccount(
      @RequestHeader(name = "Authorization") String token,
      @RequestBody AccountDeleteForm form
  ){
    return ResponseEntity.ok(
        accountService.deleteAccount(
            token.substring(7),form)
        );
  }








}
