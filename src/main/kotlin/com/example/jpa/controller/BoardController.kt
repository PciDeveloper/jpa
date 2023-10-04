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

//    {
//        "title": "제목",
//        "content": "내용",
//        "file": [
//        {
//            "file" : "파일"
//        }
//        ]
//    }

    //== 해결
    @PostMapping("/insert")
    fun insert(@RequestBody boardDTO: BoardDTO) {
        val boardEntity = boardMapStruct.toEntity(boardDTO)
        boardService.save(boardEntity)
    }

    @GetMapping("/list")
    fun list(): List<BoardDTO> {
        return boardService.list()
    }

    @PostMapping("/delete/{bono}")
    fun delete(@PathVariable("bono") bono: Int) {
        boardService.delete(bono)
    }

    @PostMapping("/update")
    fun update(@RequestBody boardDTO: BoardDTO) { // boardDTO 에 있는 bono 키 벨류를 받음
        val boardEntity = boardMapStruct.toEntity(boardDTO) // toEntity 변환
        boardService.update(boardEntity)
    }

//    @PostMapping("/update") // path value
//    fun update(bono: Int, @RequestBody boardDTO: BoardDTO) {
//        val boardEntity = boardMapStruct.toEntity(boardDTO)
//        boardService.update(bono, boardEntity)
//    }

//    @PostMapping("/update/{bono}") // queryString
//    fun update(@PathVariable("bono") bono: Int, @RequestBody boardDTO: BoardDTO) {
//        val boardEntity = boardMapStruct.toEntity(boardDTO)
//        boardService.update(bono, boardEntity)
//    }

}