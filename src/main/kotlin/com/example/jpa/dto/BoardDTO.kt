package com.example.jpa.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

//@NotNull => null을 허용하지 않는다. "", " "는 허용한다.
//@NotEmpty => null, ""을 허용하지 않는다. " "는 허용한다.
data class BoardDTO (

    var bono: Int?,

    @field:NotEmpty(message = "dto 필수 데이터 누락")
    var title: String?,

    var content: String?,
    var file: List<BoardFileDTO>
)