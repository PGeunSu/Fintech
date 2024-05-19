package com.zerobase.Fintech.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  //User
  ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다."),
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
  LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디와 패스워드를 확인해 주세요."),

  //Verify
  ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다."),
  WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
  EXPIRE_CODE(HttpStatus.BAD_REQUEST, "인증시간이 만료되었습니다");

  private final HttpStatus httpStatus;
  private final String description;
}