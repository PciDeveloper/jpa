package com.example.jpa.service

import com.example.jpa.dto.BoardDTO
import com.example.jpa.entity.BoardEntity
import com.example.jpa.mapstruct.BoardMapStruct
import com.example.jpa.repository.BoardRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BoardService @Autowired constructor(
    private val boardRepository: BoardRepository,
    private val boardMapStruct: BoardMapStruct
) {

    private val logger = LoggerFactory.getLogger(BoardService::class.java)

    @Transactional
    fun save(boardEntity: BoardEntity) {
        boardRepository.save(boardEntity)
    }

    // 데이터 변경시에는 트랜잭션이 필요하고,
    // 단순 조회는 트랜잭션(@Transactional) 사용 x
    fun list(): List<BoardDTO> {
        val find = boardRepository.findAll()
        return find.map { boardMapStruct.toDTO(it) }
    }

    // error !!!
    // java.lang.reflect.InaccessibleObjectException: Unable to make field private final java.lang.Object
    // java.util.Optional.value accessible: module java.base does not "opens java.util" to unnamed module @704921a5
//    fun detail(bono: Int): Optional<BoardDTO> {
//        val find = boardRepository.findById(bono)
//        return find.map { boardMapStruct.toDTO(it) }
//    }

    fun detail(bono: Int): ResponseEntity<Any> {
        val find = boardRepository.findById(bono)
        return ResponseEntity.ok(hashMapOf("mode" to true, "data" to find))
    }

    @Transactional
    fun delete(bono: Int) {

        val find = boardRepository.findById(bono) // 해당 게시물 bono 조회

        if (find.isPresent) {
            val deletePre = find.get() // 삭제되기 이전에 해당 bono 게시글 데이터 백업 저장
            val toDto = boardMapStruct.toDTO(deletePre) // Entity -> DTO 변환
            boardRepository.deleteById(bono) // 실제 삭제 실행 로직
            logger.info("result : $toDto") // 원본 데이터 toDTO 변환 값 출력 => 어떠한 게시글이 삭제가 이루어졌는지 보기위함
        } else {
            logger.info("해당 게시물이 없습니다.")
        }
    }

//    fun testDelete(boardEntity: BoardEntity) {
//        boardEntity.bono?.let { boardRepository.deleteById(it) }
//    }

}