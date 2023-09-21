package com.mtvs.crimecapturetv.notification.command.aggregate.entity.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {

    CRIME_VIDEO_NOTIFICATION("의심영상이 확인되었습니다."),    // 비디오가 들어왔을 때 -> 비디오 관련 매장 대상자
    REQUEST_INQUIRY_BOARD_NOTIFICATION("문의 글이 작성되었습니다."), // 문의 글이 생성되었을 때 -> admin
    INQUIRY_BOARD_NOTIFICATION("문의하신 내용의 답변이 완료되었습니다.");  // 문의글 답변이 달렸을 때 -> 작성자

    private String message;

}
