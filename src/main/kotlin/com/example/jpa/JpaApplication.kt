package com.example.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy // aop Aspect 사용 1 : 시간 측정 AOP 를 사용하기 위해 어플리케이션 레벨에 어노테이션 추가
class JpaApplication

fun main(args: Array<String>) {
	runApplication<JpaApplication>(*args)
}
