package com.example.jpa.controller

import com.example.jpa.aop.PciLog
import com.example.jpa.dto.BoardDTO
import com.example.jpa.exception.CustomExc
import com.example.jpa.mapstruct.BoardMapStruct
import com.example.jpa.service.BoardService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class BoardController @Autowired constructor(
    private val boardService: BoardService,
    private val boardMapStruct: BoardMapStruct

) {

    private val logger = LoggerFactory.getLogger(BoardController::class.java)

    @PciLog // aop 사용 4 : PciLog.kt annotation class 에서 생성한 어노테이션을 최종적으로 사용하고자하는 메서드에 적용
    @PostMapping("/save") // insert, update save 처리
    fun save(@Valid @RequestBody boardDTO: BoardDTO, bindingResult: BindingResult, request: HttpServletRequest): ResponseEntity<Any> {

        // BindingResult  객체는 유효성 검사 결과를 저장하는데 사용된다.
        // BindingResult 를 사용하기 위해 BoardDTO 필드에 요소를 지정하였다.

        // 아래와 같이 로그를 출력하였지만 TestLogAop.kt 에서 AOP 로깅을 사용하여 컨트롤러 메서드마다 출력되게 하였다.
        // logger.info("Save Parameter Log = Title: '{}', Content: '{}'", boardDTO.title, boardDTO.content)

        // BoardDTO title Notnull 설정 후 검증 로직 추가
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.allErrors // 검사 결과 발생한 오류를 errors 변수에 할당
            for (error in errors) {
                logger.warn(error.defaultMessage) // 검증 오류에 기본 메세지 전달
                throw CustomExc("Custom Exception 테스트 실행됨") // 검증 오류가 발생하면 예외 처리를 결과를 보여준다.
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

    // error !!!
    // java.lang.reflect.InaccessibleObjectException: Unable to make field private final java.lang.Object
    // java.util.Optional.value accessible: module java.base does not "opens java.util" to unnamed module @704921a5
//    @GetMapping("/detail/{bono}")
//    fun detail(@PathVariable("bono") bono: Int): Optional<BoardDTO> {
//        return boardService.detail(bono)
//    }

    @GetMapping("/detail/{bono}")
    fun detail(@PathVariable("bono") bono: Int): ResponseEntity<Any> {
        val result = boardService.detail(bono)
        return ResponseEntity.ok(hashMapOf("mode" to true, "data" to result))
    }

    @PostMapping("/delete/{bono}")
    fun delete(@PathVariable("bono") bono: Int) {
        boardService.delete(bono)
    }

//    @PostMapping("/delete")
//    fun test(boardDTO: BoardDTO) {
//        val boardEntity = boardMapStruct.toEntity(boardDTO)
//        boardService.testDelete(boardEntity)
//    }

}