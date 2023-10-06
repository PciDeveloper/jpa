package com.example.jpa.dto

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kotlinx.serialization.json.Json
import java.util.*

data class LogDTO (
    val id: Int? = null,
    val logResult: Json,
    val req: Json,
    val logDate: Date
)