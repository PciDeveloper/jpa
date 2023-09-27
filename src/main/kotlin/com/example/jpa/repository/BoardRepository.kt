package com.example.jpa.repository

import com.example.jpa.entity.BoardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardRepository : JpaRepository<BoardEntity, Int> {

    // N + 1 발생 해결 방법
    // FETCH 키워드를 사용하여 해당 컬렉션을 즉시 로드
    // join fetch"를 사용하여 게시물(Board)과 첨부 파일(BoardFile)을 함께 가져오도록 하였음
//    @Query("SELECT DISTINCT b FROM BoardEntity b LEFT JOIN FETCH b.file")
//    fun listTest(): List<BoardEntity>
//    fun findByIdWithFiles(@Param("bono") bono: Int): BoardEntity
}