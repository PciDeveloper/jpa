package com.example.jpa.entity

import jakarta.persistence.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.Date

@Entity
@Table(name = "log")
class LogEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "logResult")
    @Serializable
    val logResult: String,

    @Column(name = "req")
    @Serializable
    val req: String,

    @Column(name = "logDate")
    val logDate: Date

)