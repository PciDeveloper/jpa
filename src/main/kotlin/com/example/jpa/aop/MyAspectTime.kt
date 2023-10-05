package com.example.jpa.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

// aop 사용 2 : 각각의 API 가 수행되는 시간을 체크하기 위해 실행 시간을 탐지하는 Aspect 생성
@Aspect
@Component
class MyAspectTime {

    private val logger = LoggerFactory.getLogger(MyAspectTime::class.java)

    // 실행 시간은 API 실행 시작 전 시간과 종료 후 시간이 모두 필요하므로 @Around 어노테이션을 활용
    // @Around 를 사용하기 위해 LogMyAspectTime 이라는 어노테이션 class 파일에 클래스명과 일치시키기
    // JoinPoint 객체는 각 컨트롤러에 정의된 method들의 args(매개변수)정보를 담고 있는 객체
    // @Around 어드바이스는 앞서 설명한 어드바이스의 기능을 모두 포괄하고 있음 ()
    @Around("@annotation(com.example.jpa.aop.PciLog)")
    fun executionTime(joinPoint: ProceedingJoinPoint): Any {

        val start = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        val executionTime = System.currentTimeMillis() - start
        logger.info("${joinPoint.signature} 소요 시간 : $executionTime ms pci test")

        return proceed
    }
}