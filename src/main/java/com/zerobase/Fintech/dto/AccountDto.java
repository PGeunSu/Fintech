package com.zerobase.Fintech.dto;

import com.zerobase.Fintech.entity.Account;
import com.zerobase.Fintech.type.AccountStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

  private String accountNumber;
  private String password;
  private String holder;
  private Long balance;
  private AccountStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  public static AccountDto from(Account account){
    return AccountDto.builder()
        .accountNumber(account.getAccountNumber())
        .holder(account.getHolder())
        .balance(account.getBalance())
        .status(account.getStatus())
        .createdAt(account.getCreatedAt())
        .deletedAt(account.getDeletedAt())
        .build();
  }

}
