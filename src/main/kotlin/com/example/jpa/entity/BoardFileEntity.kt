package com.example.jpa.entity

import jakarta.persistence.*

@Entity
@Table(name = "board_file")
class BoardFileEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var bfno: Int? = null,

    @Column(name = "name")
    var file: String? = null,

    // @JoinColumn 속성
    // name => BoardEntity 에서 pk 를 참조하기 위해 매핑할 'fk 테이블의 컬럼'
    // referencedColumnName => 'fk' 가 참조하는 'pk 테이블의 컬럼'
    // '외래키'가 있는 쪽이 '주인 엔티티' 는 mappedBy 속성을 사용하지 않는다.
    // 연관관계 주인의 해당 속의 필드명 boardId 를 명시하였으므로 BoardEntity 에 mappedBy 에 들어갈 name 속성은 bono 가 된다.

    // var bono 는 BoardCommentEntity 엔티티가 특정 게시글(게시판)을 참조하기 위한 외래 키 fk 역할을 함
    // 따라서 bono 속성의 데이터 타입은 BoardEntity가 되어야 한다.
    // private BoardEntity bono; 개념
//    @ManyToOne(fetch = FetchType.LAZY) // 참조하고 있는 테이블이 연관관계 주인이 된다.
    @ManyToOne
//    @JoinColumns(JoinColumn(name = "bono", referencedColumnName = "bono"))
    @JoinColumn(name = "bono", referencedColumnName = "bono")
    var bono: BoardEntity? = null
)