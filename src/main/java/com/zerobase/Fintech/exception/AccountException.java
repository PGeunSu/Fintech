package com.zerobase.Fintech.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class AccountException extends RuntimeException{

//  private ErrorCode errorCode;
//  private HttpStatus httpStatus;
//  private String errorMessage;

  private final ErrorCode errorCode;

  public AccountException(ErrorCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
//    this.httpStatus = errorCode.getHttpStatus();
//    this.errorMessage = errorCode.getDescription();
  }
}
