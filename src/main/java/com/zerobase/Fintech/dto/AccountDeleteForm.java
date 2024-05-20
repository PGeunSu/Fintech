package com.zerobase.Fintech.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountDeleteForm {
  private Long accountId;
}
