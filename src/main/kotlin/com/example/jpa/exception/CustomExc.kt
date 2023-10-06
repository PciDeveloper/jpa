package com.example.jpa.exception

// CustomExc 라는 data class 는 RuntimeException 클래스를 상속한다.
data class CustomExc (val customEx: String) : RuntimeException(customEx)