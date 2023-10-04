package com.example.jpa.exception

import com.example.jpa.controller.BoardController
import jakarta.validation.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice // => @ControllerAdvice , @ResponseBody 를 가지고 있다.
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun boardException(ex: Exception): ResponseEntity<String> {
        val errorMessage = ex.message ?: "Global Exception Handler 필수 데이터 누락"
        logger.warn(errorMessage)
        return ResponseEntity.badRequest().body(errorMessage)
    }

}