package com.example.jpa.service

import com.example.jpa.dto.BoardDTO
import com.example.jpa.entity.BoardEntity
import com.example.jpa.mapstruct.BoardMapStruct
import com.example.jpa.repository.BoardRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BoardService @Autowired constructor(
    private val boardRepository: BoardRepository,
    private val boardMapStruct: BoardMapStruct
) {

    private val logger = LoggerFactory.getLogger(BoardService::class.java)

    @Transactional
    fun save(boardEntity: BoardEntity): BoardEntity {
        return boardRepository.save(boardEntity)
    }

    @Transactional
    fun list(): List<BoardDTO> {
        val find = boardRepository.findAll()
        return find.map { boardMapStruct.toDTO(it) }
    }

    @Transactional
    fun update(boardEntity: BoardEntity): BoardEntity {
        val bono = boardEntity.bono ?: throw IllegalArgumentException("게시물 번호 null 방지")
        val idx = boardRepository.findById(bono).orElseThrow{ NoSuchElementException("해당 게시물이 없습니다.") }
        idx.title = boardEntity.title // boardEntity 에 title 값을 해당 idx title 에 할당
        idx.content = boardEntity.content // boardEntity 에 content 값을 해당 idx content 에 할당

        idx.file.forEachIndexed { index, file -> // entity 에 idx 의 파일 목록을 순회
            if (index < boardEntity.file.size) { // 현재 인덱스가 업데이트 하려는 DTO boardEntity 의 파일 목록 크기를 넘지 않는다면 수행
                // 현재 엔티티 idx 의 파일과, 업데이트 하려는 DTO boardEntity의 파일을 매칭하여 업데이트
                // 여기서 file 은 엔티티 idx 의 file
                // updateFileList 는 DTO boardEntity의 file
                val updateFileList = boardEntity.file[index]

                // 엔티티 idx 의 파일(file)의 file 필드를 업데이트 하려는 DTO boardEntity의 파일(updateFileList)의 file 값으로 설정
                file.file = updateFileList.file
            }
        }
        return boardRepository.save(idx)
    }

    @Transactional
    fun delete(bono: Int) {
        boardRepository.deleteById(bono)
    }

//    @Transactional
//    fun update(bono: Int, boardEntity: BoardEntity): BoardEntity {
//        val idx = boardRepository.findById(bono).orElseThrow{ NoSuchElementException("해당 게시물이 없습니다.") }
//        idx.title = boardEntity.title
//        idx.content = boardEntity.content
//
//        idx.file.forEachIndexed { index, file ->
//            if (index < boardEntity.file.size) {
//                val updateFileList = boardEntity.file[index]
//                file.file = updateFileList.file
//            }
//        }
//        return boardRepository.save(idx)
//    }


//    fun list(): List<BoardDTO> {
//        val boardsWithFiles = boardRepository.listTest()
//        return boardsWithFiles.map { boardMapStruct.toDTO(it) }
//    }

}