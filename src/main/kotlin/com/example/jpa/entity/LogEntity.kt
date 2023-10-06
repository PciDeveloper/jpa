package com.example.jpa.entity

import jakarta.persistence.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.Date

@Entity
@Table(name = "log")
@Serializable
class LogEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "logResult")
    val logResult: Json,

    @Column(name = "req")
    val req: Json,

    @Column(name = "logDate")
    val logDate: Date

)