package com.example.jpa.exception

enum class CustomExceptionType(val code: Int) {
    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500)
}