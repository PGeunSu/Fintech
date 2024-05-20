package com.zerobase.Fintech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateForm {

  private String accountNumber;
  private String password;
  private String holder;

}
