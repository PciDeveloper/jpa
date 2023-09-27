package com.example.jpa.service

import com.example.jpa.dto.BoardDTO
import com.example.jpa.dto.BoardFileDTO
import com.example.jpa.entity.BoardEntity
import com.example.jpa.mapstruct.BoardFileMapStruct
import com.example.jpa.mapstruct.BoardMapStruct
import com.example.jpa.repository.BoardFileRepository
import com.example.jpa.repository.BoardRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BoardService @Autowired constructor(
    private val boardRepository: BoardRepository,
//    private val boardFileRepository: BoardFileRepository,
    private val boardMapStruct: BoardMapStruct,
//    private val boardFileMapStruct: BoardFileMapStruct
) {

    private val logger = LoggerFactory.getLogger(BoardService::class.java)


//    @Transactional
//    fun insert(boardDTO: BoardDTO): BoardDTO {
//        logger.warn("boardCommentService commentSave {}", boardDTO)
//
//        val entity = boardMapStruct.toEntity(boardDTO)
//        logger.warn("boardCommentService entity {}", entity.bono)
//
//        val saveBoard = boardRepository.save(entity)
//        logger.warn("BoardService saveBoard : {}", saveBoard)
//
//        val result = boardMapStruct.toDTO(saveBoard)
//        logger.warn("BoardService result : {}", result)
//
//        return result
//    }

    @Transactional
    fun save(boardEntity: BoardEntity): BoardEntity {
        return boardRepository.save(boardEntity)
    }

    // -- 해결
//    @Transactional
//    fun insert(boardDTO: BoardDTO): BoardDTO {
//
//        val boardEntity = boardMapStruct.toEntity(boardDTO)
//        val saveBoard = boardRepository.save(boardEntity)
//
//        // boardDTO 에 있는 BoardFileDTO 에 file 리스트를 사용하여 BoardFileEntity 리스트 생성
//        val boardFileEntity = boardDTO.file.map { boardFileMapStruct.toEntity(it) }
//
//        // 생성된 BoardFileEntity 리스트의 각 요소에 대하여 반복하고
//        boardFileEntity.forEach { it.bono = saveBoard }
//
//        // BoardFileEntity 리스트를 DB 에 저장
//        boardFileRepository.saveAll(boardFileEntity)
//        return boardMapStruct.toDTO(saveBoard)
//    }

    @Transactional
    fun list(): List<BoardDTO> {
        val find = boardRepository.findAll()
        return find.map { boardMapStruct.toDTO(it) }
    }

//    fun list(): List<BoardDTO> {
//        val boardsWithFiles = boardRepository.listTest()
//        return boardsWithFiles.map { boardMapStruct.toDTO(it) }
//    }

    @Transactional
    fun update(bono: Int, boardEntity: BoardEntity): BoardEntity {
//        val idx = boardRepository.findById(bono).get()
        val idx = boardRepository.findById(bono).orElseThrow{ NoSuchElementException("게시물이 없습니다.") }
        idx.title = boardEntity.title
        idx.content = boardEntity.content

        idx.file.forEachIndexed { index, file ->
            if (index < boardEntity.file.size) {
                val updateFileList = boardEntity.file[index]
                file.file = updateFileList.file
            }
        }

        return boardRepository.save(idx)
    }



}