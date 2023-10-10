package com.example.jpa.service

import com.example.jpa.dto.BoardDTO
import com.example.jpa.entity.BoardEntity
import com.example.jpa.mapstruct.BoardMapStruct
import com.example.jpa.repository.BoardRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

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
    // 단순 조회는 트랜잭션(@Transactional) 없이 읽기가 가능하다.
    fun list(): List<BoardDTO> {
        val find = boardRepository.findAll()
        return find.map { boardMapStruct.toDTO(it) }
    }

    fun detail(bono: Int): Optional<BoardDTO> {
        val find = boardRepository.findById(bono)
        return find.map { boardMapStruct.toDTO(it) }
    }

    @Transactional
    fun delete(bono: Int) {
        boardRepository.deleteById(bono)
    }

//    @Transactional
//    fun delete(boardEntity: BoardEntity) {
//        boardEntity.bono?.let { boardRepository.deleteById(it) }
//    }

}