package com.example.jpa.entity

import jakarta.persistence.*

@Entity
@Table(name = "board")
class BoardEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bono")
    var bono: Int? = null,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "content")
    var content: String? = null,

    // mappedBy => BoardFileEntity 에서 엔티티를 참조하는 필드 이름 bono
    // var name 은 filename 을 칭함
    @OneToMany(mappedBy = "bono", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var file: List<BoardFileEntity> = mutableListOf()
)