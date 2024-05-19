package com.zerobase.Fintech.entity;

import com.zerobase.Fintech.type.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Transaction {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private Long id;

  private String withdrawAccount; //출금계좌
  private String depositAccount; //입금계좌
  private Long transactionAmount; //거래금액

  private Long beforeAmount; //거래 전 금액
  private Long currentAmount; //현재 금액

  private LocalDateTime transactionAt;

  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

//  @JoinColumn(name = "account_id")
//  private Account account;


}
