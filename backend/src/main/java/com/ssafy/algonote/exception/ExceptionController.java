package com.ssafy.algonote.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
        CustomException.class
    })
    public ResponseEntity<ExceptionResponse> customRequestException
        (final CustomException e) {
        log.warn("api Exception : {}", e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(new ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class ExceptionResponse {
        private ErrorCode errorCode;
        private String message;
    }

}
