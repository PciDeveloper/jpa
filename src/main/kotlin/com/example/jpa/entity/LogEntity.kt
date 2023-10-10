package com.example.jpa.entity

import com.google.gson.JsonObject
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

//    @Column(name = "logResult", columnDefinition = "json")
    @Column(name = "logResult")
    val logResult: String,

//    @Column(name = "req", columnDefinition = "json")
    @Column(name = "req")
    val req: String,

    @Column(name = "logDate")
    val logDate: Date

)