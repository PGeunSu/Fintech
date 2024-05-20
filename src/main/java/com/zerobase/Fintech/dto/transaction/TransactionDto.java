package com.zerobase.Fintech.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.zerobase.Fintech.type.TransactionType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionDto {

    private Long id;

    private String transactionTargetName;

    private Long balance;

    private TransactionType type;

    private LocalDateTime transactedAt;
}