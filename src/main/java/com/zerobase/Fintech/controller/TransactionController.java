package com.zerobase.Fintech.controller;

import com.zerobase.Fintech.dto.transaction.DepositDto;
import com.zerobase.Fintech.dto.transaction.RemittanceDto;
import com.zerobase.Fintech.dto.transaction.WithdrawDto;
import com.zerobase.Fintech.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

  private final TransactionService transactionService;


  @PutMapping("/deposit")
  public ResponseEntity<DepositDto.Response> deposit(
      @RequestBody DepositDto.Request request
  ){
    DepositDto.Response response = transactionService.deposit(request);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/withdraw")
  public ResponseEntity<WithdrawDto.Response> withdraw(
      @RequestHeader(name = "Authorization") String token,
      @RequestBody WithdrawDto.Request request
  ) {
    WithdrawDto.Response response = transactionService.withdraw(
        token.substring(7), request);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/remittance")
  public ResponseEntity<RemittanceDto.Response> remittance(
      @RequestHeader(name = "Authorization") String token,
      @RequestBody RemittanceDto.Request request
  ) {
    return ResponseEntity.ok(
        transactionService.remittance(token.substring(7), request)
    );
  }

}
