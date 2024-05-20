package com.zerobase.Fintech.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  //User
  ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다."),
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
  LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디와 패스워드를 확인해 주세요."),

  //Verify
  ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다."),
  WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
  EXPIRE_CODE(HttpStatus.BAD_REQUEST, "인증시간이 만료되었습니다"),

  //Account
  MAX_COUNT_ACCOUNT(HttpStatus.BAD_REQUEST, "최대 계좌 개수는 5개 입니다."),
  ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 계좌가 존재하지 않습니다."),
  ALREADY_REGISTER_ACCOUNT(HttpStatus.BAD_REQUEST, "이미 존재하는 계좌입니다."),
  USER_ACCOUNT_UNMATCH(HttpStatus.BAD_REQUEST, "사용자의 아이디와 계좌 소유주가 일치하지 않습니다."),
  ACCOUNT_HAS_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 있는 계자는 해지할 수 없습니다."),

  //Transaction
  SENT_ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST, "송금 보내는 계좌를 찾을 수 없습니다."),
  RECEIVED_ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST, "송금 받는 계좌를 찾을 수 없습니다."),
  BALANCE_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "계좌의 잔액이 부족합니다."),
  TRANSACTION_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "거래 종류를 확인할 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String description;
}
