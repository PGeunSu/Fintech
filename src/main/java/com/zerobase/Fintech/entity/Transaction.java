package com.zerobase.Fintech.entity;

import com.zerobase.Fintech.type.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
public class Transaction {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private Long id;

  private Long balance;

  private String depositName; // 입금자명

  private String withdrawName; // 출금자명

  private String receivedName; // 송금받는 계좌주명

  private String receivedAccount; // 송금받는 계좌번호

  @CreatedDate
  private LocalDateTime transactionAt;

  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  @ManyToOne
  private Account account;


}
