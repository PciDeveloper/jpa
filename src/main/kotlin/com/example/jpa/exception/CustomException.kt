package com.example.jpa.exception

data class CustomException (val customEx: String) : RuntimeException(customEx)