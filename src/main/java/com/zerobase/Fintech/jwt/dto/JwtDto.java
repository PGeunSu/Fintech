package com.zerobase.Fintech.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtDto {

  private String grantType;
  private String accessToken;
  private String refreshToken;

}
