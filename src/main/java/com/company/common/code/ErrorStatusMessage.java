package com.company.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
@Getter
public enum ErrorStatusMessage {

    INPUT_UNVALID_ERROR(HttpStatus.BAD_REQUEST, "입력값이 불충분합니다."),
    INPUT_FILE_ERROR(HttpStatus.BAD_REQUEST, "첨부 파일을 확인해주세요."),
    USER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당 직원이 없습니다."),
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "알수없는 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}