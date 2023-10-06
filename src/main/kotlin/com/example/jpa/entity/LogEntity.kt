package com.example.jpa.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "log")
data class LogEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "logResult")
    val logResult: String,

    @Column(name = "logDate")
    val logDate: Date

)