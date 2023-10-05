package com.example.jpa.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

// 각각의 API 가 수행되는 시간을 체크하기 위해 실행 시간을 탐지하는 Aspect 생성
@Aspect
@Component
class MyAspectTime {

    private val logger = LoggerFactory.getLogger(MyAspectTime::class.java)

    // 실행 시간은 API 실행 시작 전 시간과 종료 후 시간이 모두 필요하므로 @Around 어노테이션을 활용
    // @Around 를 사용하기 위해 LogMyAspectTime 이라는 어노테이션 class 파일에 클래스명과 일치시키기
    @Around("@annotation(com.example.jpa.aop.LogExecutionTime)")
    fun executionTime(joinPoint: ProceedingJoinPoint): Any {

        val start = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        val executionTime = System.currentTimeMillis() - start
        logger.info("${joinPoint.signature} 소요 시간 : $executionTime ms pci test") // 로그 남기기

        return proceed
    }
}