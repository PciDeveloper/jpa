package com.example.jpa.controller

import com.example.jpa.dto.BoardDTO
import com.example.jpa.entity.BoardEntity
import com.example.jpa.exception.GlobalExceptionHandler
import com.example.jpa.mapstruct.BoardMapStruct
import com.example.jpa.service.BoardService
import jakarta.validation.Valid
import jakarta.validation.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class BoardController @Autowired constructor(
    private val boardService: BoardService,
    private val boardMapStruct: BoardMapStruct
) {

    private val logger = LoggerFactory.getLogger(BoardController::class.java)

    @PostMapping("/save") // insert, update save 처리
    fun save(@Valid @RequestBody boardDTO: BoardDTO, bindingResult: BindingResult): ResponseEntity<Any> {

        // BoardDTO title Notnull 설정 후 임시 검증 로직 추가
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.allErrors
            for (error in errors) {
                logger.warn(error.defaultMessage)
                throw Exception()
            }
        }

        val boardEntity = boardMapStruct.toEntity(boardDTO)
        boardService.save(boardEntity)

        return ResponseEntity.ok(hashMapOf("mode" to true, "data" to boardDTO))
    }

    @GetMapping("/list")
    fun list(): List<BoardDTO> {
        return boardService.list()
    }

    @GetMapping("/detail/{bono}")
    fun detail(@PathVariable("bono") bono: Int): Optional<BoardDTO> {
        return boardService.detail(bono)
    }

    @PostMapping("/delete/{bono}")
    fun delete(@PathVariable("bono") bono: Int) {
        boardService.delete(bono)
    }

}