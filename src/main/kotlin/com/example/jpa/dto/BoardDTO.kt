package com.example.jpa.dto


data class BoardDTO (
    var bono: Int?,
    var title: String?,
    var content: String?,
    var file: List<BoardFileDTO>
)