package com.example.jpa.mapper

interface BoardFileEntityMapper<E, D> {
    fun toDTO(entity: E): D // BoardFileEntity => BoardFileDTO 변환
    fun toEntity(dto: D): E // BoardFileDTO => BoardFileEntity 변환
}