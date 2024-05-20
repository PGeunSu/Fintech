package com.zerobase.Fintech.service;

import static com.zerobase.Fintech.exception.ErrorCode.ACCOUNT_HAS_BALANCE;
import static com.zerobase.Fintech.exception.ErrorCode.ACCOUNT_NOT_FOUND;
import static com.zerobase.Fintech.exception.ErrorCode.MAX_COUNT_ACCOUNT;
import static com.zerobase.Fintech.exception.ErrorCode.USER_ACCOUNT_UNMATCH;
import static com.zerobase.Fintech.exception.ErrorCode.USER_NOT_FOUND;
import static com.zerobase.Fintech.type.AccountStatus.ACTIVE;
import static com.zerobase.Fintech.type.AccountStatus.INACTIVE;

import com.zerobase.Fintech.dto.AccountCreateForm;
import com.zerobase.Fintech.dto.AccountDeleteForm;
import com.zerobase.Fintech.dto.AccountDto;
import com.zerobase.Fintech.entity.Account;
import com.zerobase.Fintech.entity.User;
import com.zerobase.Fintech.exception.AccountException;
import com.zerobase.Fintech.jwt.config.JwtTokenProvider;
import com.zerobase.Fintech.repository.AccountRepository;
import com.zerobase.Fintech.repository.UserRepository;
import com.zerobase.Fintech.type.AccountStatus;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;


  @Transactional
  public AccountDto createAccount(String token, AccountCreateForm form){
    //토큰 사용자와 삭제를 요청한 계좌의 id 비교
    Long tokenUser = jwtTokenProvider.getId(token);
    if (!Objects.equals(tokenUser, form.getUserId())){
      throw new AccountException(USER_ACCOUNT_UNMATCH);
    }
//    //소유주가 보유한 계좌가 5개 이상인지 체크
//    countedAccount((User) accountRepository.findByHolder(name));

    User user = userRepository.findById(tokenUser)
        .orElseThrow(()-> new AccountException(USER_NOT_FOUND));

    return AccountDto.from(
        accountRepository.save(
            Account.builder()
                .user(user)
                .accountNumber(form.getAccountNumber())
                .password(form.getPassword())
                .holder(form.getHolder())
                .balance(0L)
                .status(ACTIVE)
                .build()
            )
        );
  }

  @Transactional
  public AccountDto deleteAccount(String token, AccountDeleteForm form){
    Account account = accountRepository.findById(form.getAccountId())
        .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));

    //토큰 사용자와 삭제를 요청한 계좌의 id 비교
    Long tokenUser = jwtTokenProvider.getId(token);
    if (!Objects.equals(tokenUser, account.getUser().getId())){
      throw new AccountException(USER_ACCOUNT_UNMATCH);
    }

    //계좌의 잔액이 0이 아니면 삭제X
    if (account.getBalance() != 0){
      throw new AccountException(ACCOUNT_HAS_BALANCE);
    }

    account.setStatus(INACTIVE);
    account.setDeletedAt(LocalDateTime.now());

    return AccountDto.from(account);

  }

    public void countedAccount(User user){
      if (accountRepository.countByHolder(user.getName()) > 5){
        throw new AccountException(MAX_COUNT_ACCOUNT);
      }
    }
  }
