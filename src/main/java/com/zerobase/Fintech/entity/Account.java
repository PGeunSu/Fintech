package com.zerobase.Fintech.entity;

import com.zerobase.Fintech.type.AccountStatus;
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
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(value = AuditingEntityListener.class)
public class Account {


  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long id;

  @Column(unique = true)
  private String accountNumber;
  private String password;
  private String holder;
  private Long balance;


  @Enumerated(EnumType.STRING)
  private AccountStatus status;

  @CreatedDate
  private LocalDateTime createdAt;

  private LocalDateTime deletedAt;

  @ManyToOne
  private User user;





}
