package com.zerobase.Fintech.dto.transaction;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class WithdrawDto {

    @Getter
    public static class Request {

        private String accountNumber;
        private String withdrawName;
        private Long balance;
    }

    @Getter
    @Builder
    public static class Response {

        private String accountNumber;
        private String withdrawName;
        private Long balance;
        private LocalDateTime transacted_at;
    }
}
