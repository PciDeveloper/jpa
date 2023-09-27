package com.example.jpa.repository

import com.example.jpa.entity.BoardFileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardFileRepository : JpaRepository<BoardFileEntity, Int> {
}