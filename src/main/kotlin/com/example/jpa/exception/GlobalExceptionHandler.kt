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

    @ExceptionHandler(CustomException::class)
    fun boardException(ex: CustomException): ResponseEntity<String> {
        val errorMessage = ex.customEx
        logger.warn(errorMessage)
        return ResponseEntity.badRequest().body(errorMessage)
    }

}