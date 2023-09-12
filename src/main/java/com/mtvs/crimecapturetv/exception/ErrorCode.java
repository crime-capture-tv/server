package com.mtvs.crimecapturetv.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_ID(HttpStatus.CONFLICT, "User Id가 중복됩니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러"),
    ;

    private HttpStatus status;
    private String message;

}
