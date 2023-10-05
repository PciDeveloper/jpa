package com.example.jpa.exception

data class CustomException (val customEx: String) : RuntimeException(customEx)
//data class CustomException (val customEx: String, val type: CustomExceptionType) : RuntimeException(customEx)