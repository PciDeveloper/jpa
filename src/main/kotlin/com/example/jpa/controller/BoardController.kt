package com.example.jpa.controller

import com.example.jpa.dto.BoardDTO
import com.example.jpa.mapstruct.BoardMapStruct
import com.example.jpa.service.BoardService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController @Autowired constructor(
    private val boardService: BoardService,
    private val boardMapStruct: BoardMapStruct

) {

    private val logger = LoggerFactory.getLogger(BoardController::class.java)

    //== 해결
//    @PostMapping("/insert")
//    fun insert(@RequestBody boardDTO: BoardDTO): BoardDTO {
//        val boardEntity = boardMapStruct.toEntity(boardDTO)
//        return boardService.insert(boardDTO)
//    }

    @PostMapping("/insert")
    fun insert(@RequestBody boardDTO: BoardDTO) {
        val boardEntity = boardMapStruct.toEntity(boardDTO)
        boardService.save(boardEntity)
    }

    @GetMapping("/list")
    fun list(): List<BoardDTO> {
        return boardService.list()
    }

    // queryString 방식 PathVariable 생략 불가능
    @PostMapping("/update/{bono}")
    fun update(@PathVariable("bono") bono: Int, @RequestBody boardDTO: BoardDTO) {
        val boardEntity = boardMapStruct.toEntity(boardDTO)
        boardService.update(bono, boardEntity)
    }

    // path value 방식 PathVariable 생략 가능
//    @PostMapping("/update")
//    fun update(bono: Int, @RequestBody boardDTO: BoardDTO) {
//        val boardEntity = boardMapStruct.toEntity(boardDTO)
//        boardService.update(bono, boardEntity)
//    }


}