package com.example.jpa.controller

import com.example.jpa.aop.LogExecutionTime
import com.example.jpa.dto.BoardDTO
import com.example.jpa.exception.CustomException
import com.example.jpa.mapstruct.BoardMapStruct
import com.example.jpa.service.BoardService
import jakarta.validation.Valid
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

    // aop 사용 3 : 최종 적용
    // @Around , LogExecutionTime annotation class 에서 생성한 어노테이션을 사용하고자하는 메서드에 적용
    @LogExecutionTime
    @PostMapping("/save") // insert, update save 처리
    fun save(@Valid @RequestBody boardDTO: BoardDTO, bindingResult: BindingResult): ResponseEntity<Any> {

        // 아래와 같이 로그를 출력하였지만 AOP 로깅을 사용하여 컨트롤러 메서드마다
        // parameter 키 벨류, 메서드 이름 등 출력되게 하였음 TestLogAop.kt 참고
        // logger.info("Save Parameter Log = Title: '{}', Content: '{}'", boardDTO.title, boardDTO.content)

        // BoardDTO title Notnull 설정 후 임시 검증 로직 추가
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.allErrors
            for (error in errors) {
                logger.warn(error.defaultMessage)
                throw CustomException("Custom Exception 테스트 실행됨")
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