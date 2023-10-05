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

    @Column(name = "log_result")
    val logResult: String,

    @Column(name = "log_Date")
    val logDate: Date

)