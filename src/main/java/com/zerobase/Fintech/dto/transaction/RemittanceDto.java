package com.zerobase.Fintech.dto.transaction;

import lombok.Builder;
import lombok.Getter;

public class RemittanceDto {

    @Getter
    public static class Request {

        private String sentAccountNumber;
        private String receivedAccountNumber;
        private Long balance;
    }

    @Getter
    @Builder
    public static class Response {

        private String sentAccountNumber;

        private String receivedAccountNumber;

        private String receivedName;

        private Long balance;
    }
}