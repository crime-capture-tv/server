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
    STORE_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 점포를 찾을 수 없습니다.."),
    CRIME_VIDEO_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 영상을 찾을 수 없습니다."),
    AI_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AI 서버와 통신이 되지 않습니다."),
    FILE_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 경로의 파일을 찾을 수 없습니다."),
    FILE_DELETE_FAILED(HttpStatus.NOT_ACCEPTABLE, "해당 파일을 삭제할 수 없습니다."),
    NOTIFICATION_NOT_FOUNDED(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "패스워드가 잘못되었습니다."),
    BOARD_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 글이 없습니다.");


    private HttpStatus status;
    private String message;

}
