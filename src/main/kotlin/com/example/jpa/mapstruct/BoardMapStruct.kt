package com.example.jpa.mapstruct

import com.example.jpa.controller.BoardController
import com.example.jpa.dto.BoardDTO
import com.example.jpa.dto.BoardFileDTO
import com.example.jpa.entity.BoardEntity
import com.example.jpa.entity.BoardFileEntity
import com.example.jpa.mapper.BoardEntityMapper
import org.mapstruct.AfterMapping
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

//@Mapper(componentModel = "spring", imports = [BoardFileMapStruct::class], uses = [BoardFileMapStruct::class])
@Mapper(componentModel = "spring", uses = [BoardFileMapStruct::class]) // uses 추가
@Component
interface BoardMapStruct: BoardEntityMapper<BoardEntity, BoardDTO> {

    // BoardEntity 와 BoardDTO 필드를 매핑
    // target => Entity 클래스 필드 이름, source => DTO 클래스 필드 이름
    // EntityMapper 인터페이스 override
    // toEntity, toDTO 메서드 => DTO 객체를 Entity 객체로 변환, Entity 객체를 DTO 객체로 변환
    @Mappings (
        Mapping(target = "bono", source = "bono"),
        Mapping(target = "title", source = "title"),
        Mapping(target = "content", source = "content"),
        Mapping(target = "file", source = "file")
    )
    override fun toEntity(dto: BoardDTO): BoardEntity

    @InheritInverseConfiguration // 부모 매퍼 메서드의 매핑 구성을 상속 하도록 지정
    override fun toDTO(entity: BoardEntity): BoardDTO

    companion object {
        private val logger = LoggerFactory.getLogger(BoardController::class.java)
//        private lateinit var boardFileMapStruct: BoardFileMapStruct
//        private lateinit var boardFileEntity: BoardFileEntity
//        private lateinit var boardFileDTO: BoardFileDTO

        @AfterMapping @JvmStatic
        fun after(boardDTO: BoardDTO, @MappingTarget entity: BoardEntity) {

//            boardDTO.file.forEach {
//                boardFileEntity -> boardFileEntity.bono = entity.bono
//                logger.warn("AfterMapping boardFileEntity.file {}", boardFileEntity.bono)
//            }

//            boardDTO.file.forEach { boardFileDTO ->
//                boardFileDTO.bono = boardDTO.bono
//            }

//            boardDTO.file.forEach { boardFileDTO ->
//                boardFileDTO.bono = boardDTO.bono
//            }

            entity.file.forEach { file -> file.bono = entity }

        }
    }

}