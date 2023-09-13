package com.mtvs.crimecapturetv.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_ID(HttpStatus.CONFLICT, "User Id가 중복됩니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러"),
    USER_NOT_FOUNDED(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    ;

    private HttpStatus status;
    private String message;

}
