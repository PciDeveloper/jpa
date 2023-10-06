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

    // PciLog 라는 어노테이션을 사용하기 위하여 @Around 에 @annotation 지정하여 PciLog.kt 에서 어노테이션 생성
    // PciLog 어노테이션이 실행되기 전과 후에 executionTime 메서드가 실행된다.
    // JoinPoint 는 메서드의 실행 결과를 조회만 할 수 있고,
    // ProceedingJoinPoint 는 메서드의 실행을 조작하고 결과를 변경하는데 사용된다.
    // 실행 시간은 API 실행 시작 전 시간과 종료 후 시간이 모두 필요하므로 ProceedingJoinPoint 어노테이션을 활용
    // @Around 에서 지정한 어노테이션을 사용하기 위해 PciLog.kt 에 어노테이션 class 와 일치시키기
    // @Around 어드바이스는 앞서 설명한 어드바이스의 기능을 모두 포괄하고 있음 ()
    @Around("@annotation(com.example.jpa.aop.PciLog)")
    fun executionTime(joinPoint: ProceedingJoinPoint): Any {

        val start = System.currentTimeMillis() // 메서드 실행 시작 시간을 기록하기 위해 현재 시간을 밀리초로 얻어온다.
        val proceed = joinPoint.proceed() // PciLog 가 사용되고 있는 메서드를 실행한다.
        val executionTime = System.currentTimeMillis() - start // 현재 시간과 실행 시간을 비교하여 실행 시간을 계산한다.

        // 실행 시간과메서드의 서명 정보를 로그로 출력한다. 이로써 어떤 메서드가 얼마나 걸렸는지 로깅한다.
        logger.info("${joinPoint.signature} 소요 시간 : $executionTime ms pci test")

        // 실제 메서드의 실행 결과를 반환한다.
        return proceed
    }
}