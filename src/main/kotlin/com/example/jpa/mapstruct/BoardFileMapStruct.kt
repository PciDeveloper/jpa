package com.example.jpa.mapstruct

import com.example.jpa.dto.BoardFileDTO
import com.example.jpa.entity.BoardFileEntity
import com.example.jpa.mapper.BoardFileEntityMapper
import org.mapstruct.*
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring") // , uses = [BoardMapStruct::class]
@Component
interface BoardFileMapStruct: BoardFileEntityMapper<BoardFileEntity, BoardFileDTO> {

    // BoardFileEntity 와 BoardFileDTO 필드를 매핑
    // target => Entity 클래스 필드 이름, source => DTO 클래스 필드 이름
    // BoardFileEntityMapper 인터페이스 override
    // toEntity, toDTO 메서드 => DTO 객체를 Entity 객체로 변환, Entity 객체를 DTO 객체로 변환
    // bono 의 데이터 타입 BoardEntity 의 bono 타입과 일치하게 하였음 => 엔티티, dto 간의 타입 에러 이유
    // . 을 사용하여 객체와 같은 접근 가능
    @Mappings(
        Mapping(target = "bfno", source = "bfno"),
        Mapping(target = "file", source = "file"),
        Mapping(target = "bono.bono", source = "bono")
    )
    override fun toEntity(dto: BoardFileDTO): BoardFileEntity

    @InheritInverseConfiguration // 부모 매퍼 메서드의 매핑 구성을 상속
    override fun toDTO(entity: BoardFileEntity): BoardFileDTO

    companion object {
        @AfterMapping @JvmStatic
        fun after(dto: BoardFileDTO, @MappingTarget entity: BoardFileEntity) {
            // entity 의 boardIdx 가 null 이거나 bono 가 null 인지 확인
//            if (entity.bono?.bono == null) entity.bono = null
//            if (entity.bono?.bono == null) entity.bono?.bono = test.bono
            if (entity.bono?.bono == null) entity.bono = null
        }
    }

}