package com.example.jpa.repository

import com.example.jpa.entity.LogEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LogEntryRepository : JpaRepository<LogEntity, Int> {
}