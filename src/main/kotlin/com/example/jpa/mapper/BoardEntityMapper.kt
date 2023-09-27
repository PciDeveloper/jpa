package com.example.jpa.mapper

// 4. BoardEntityMapper 인터페이스

interface BoardEntityMapper<E, D> {
    fun toDTO(entity: E): D // BoardEntity => BoardDTO 변환
    fun toEntity(dto: D): E // BoardDTO => BoardEntity 변환
}