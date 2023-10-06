package com.example.jpa.exception

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// @RestControllerAdvice 는 @ControllerAdvice , @ResponseBody 를 모두 가지고 있다.
// 예외 처리 결과를 HTTP 응답으로 반환할 수 있다.
@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // @ExceptionHandler 어노테이션은 CustomExc 클래스에서 발생하는 예외를 처리하는 메서드를 정의한다.
    // 즉, CustomExc 예외가 발생할 경우 이 메서드가 실행된다.
    @ExceptionHandler(CustomExc::class)
    fun boardException(ex: CustomExc): ResponseEntity<String> { // CustomExc 예외를 처리하는 메서드.
        val errorMessage = ex.customEx // CustomExc 클래스에 있는 customEx 프로퍼티의 값을 추출하여 변수에 할당
        logger.warn(errorMessage)
        return ResponseEntity.badRequest().body(errorMessage) // badRequest HTTP 400 에러일 때 메세지 반환
    }

}