package com.zerobase.Fintech.service;

import static com.zerobase.Fintech.exception.ErrorCode.ACCOUNT_NOT_FOUND;
import static com.zerobase.Fintech.exception.ErrorCode.BALANCE_NOT_ENOUGH;
import static com.zerobase.Fintech.exception.ErrorCode.RECEIVED_ACCOUNT_NOT_FOUND;
import static com.zerobase.Fintech.exception.ErrorCode.SENT_ACCOUNT_NOT_FOUND;
import static com.zerobase.Fintech.exception.ErrorCode.USER_ACCOUNT_UNMATCH;
import static com.zerobase.Fintech.type.AccountStatus.INACTIVE;
import static com.zerobase.Fintech.type.TransactionType.*;
import static com.zerobase.Fintech.type.TransactionType.DEPOSIT;
import static com.zerobase.Fintech.type.TransactionType.WITHDRAW;

import com.zerobase.Fintech.dto.transaction.DepositDto;
import com.zerobase.Fintech.dto.transaction.RemittanceDto;
import com.zerobase.Fintech.dto.transaction.WithdrawDto;
import com.zerobase.Fintech.entity.Account;
import com.zerobase.Fintech.entity.Transaction;
import com.zerobase.Fintech.exception.AccountException;
import com.zerobase.Fintech.exception.ErrorCode;
import com.zerobase.Fintech.jwt.config.JwtTokenProvider;
import com.zerobase.Fintech.repository.AccountRepository;
import com.zerobase.Fintech.repository.TransactionRepository;
import com.zerobase.Fintech.type.AccountStatus;
import com.zerobase.Fintech.type.TransactionType;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  private final JwtTokenProvider jwtTokenProvider;


  //입금
  @Transactional
  public DepositDto.Response deposit(DepositDto.Request request){
    //계좌 존재 여부
    Account account = accountRepository.findByAccountNumber(
        request.getAccountNumber())
        .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));

    if (account.getStatus().equals(INACTIVE)){
      throw new AccountException(ACCOUNT_NOT_FOUND);
    }

    //금액 조정
    account.setBalance(account.getBalance() + request.getBalance());

    // 거래 내역 테이블에 거래추가
    transactionRepository.save(
        Transaction.builder()
            .account(account)
            .transactionType(DEPOSIT)
            .balance(request.getBalance())
            .depositName(request.getDepositName())
            .build()
    );

    return DepositDto.Response.builder()
        .accountNumber(request.getAccountNumber())
        .depositName(request.getDepositName())
        .balance(request.getBalance())
        .transacted_at(LocalDateTime.now())
        .build();
  }

  //출금
  @Transactional
  public WithdrawDto.Response withdraw(String token, WithdrawDto.Request request){
    Long tokenUser = jwtTokenProvider.getId(token);
    Account account = accountRepository.findByAccountNumber(
        request.getAccountNumber())
        .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));

    //계좌가 해지상태일 경우
    if (account.getStatus().equals(INACTIVE)){
      throw new AccountException(ACCOUNT_NOT_FOUND);
    }
    //소유주 확인
    if (!Objects.equals(tokenUser, account.getUser().getId())){
      throw new AccountException(USER_ACCOUNT_UNMATCH);
    }
    //출금 금액이 잔액보다 많을 경우
    if (request.getBalance() > account.getBalance()){
      throw new AccountException(BALANCE_NOT_ENOUGH);
    }
    //금액 조정
    account.setBalance(account.getBalance() - request.getBalance());

    transactionRepository.save(
        Transaction.builder()
            .account(account)
            .transactionType(WITHDRAW)
            .balance(request.getBalance())
            .withdrawName(request.getWithdrawName())
            .build()
    );

    return WithdrawDto.Response.builder()
        .accountNumber(request.getAccountNumber())
        .withdrawName(request.getWithdrawName())
        .balance(request.getBalance())
        .transacted_at(LocalDateTime.now())
        .build();

  }

  //송금
  @Transactional
  public RemittanceDto.Response remittance(String token, RemittanceDto.Request request){
    Account sentAccount = accountRepository.findByAccountNumber(
            request.getSentAccountNumber())
        .orElseThrow(() -> new AccountException(SENT_ACCOUNT_NOT_FOUND));

    Account recievedAccount = accountRepository.findByAccountNumber(
            request.getReceivedAccountNumber())
        .orElseThrow(() -> new AccountException(RECEIVED_ACCOUNT_NOT_FOUND));

    Long tokenUser = jwtTokenProvider.getId(token);

    if (!Objects.equals(tokenUser, sentAccount.getUser().getId())) {
      throw new AccountException(USER_ACCOUNT_UNMATCH);
    }

    //계좌 해지 여부
    if (sentAccount.getStatus().equals(INACTIVE)){
      throw new AccountException(SENT_ACCOUNT_NOT_FOUND);
    }else if (recievedAccount.getStatus().equals(INACTIVE)){
      throw new AccountException(RECEIVED_ACCOUNT_NOT_FOUND);
    }

    //금액 조정
    sentAccount.setBalance(sentAccount.getBalance() - request.getBalance());
    recievedAccount.setBalance(recievedAccount.getBalance() + request.getBalance());

    transactionRepository.save(
        Transaction.builder()
            .account(sentAccount)
            .transactionType(REMITTANCE)
            .balance(request.getBalance())
            .receivedName(recievedAccount.getUser().getUsername())
            .receivedAccount(request.getReceivedAccountNumber())
            .build()
    );

    return RemittanceDto.Response.builder()
        .sentAccountNumber(request.getSentAccountNumber())
        .receivedAccountNumber(request.getReceivedAccountNumber())
        .receivedName(recievedAccount.getUser().getUsername())
        .balance(request.getBalance())
        .build();

  }



}
