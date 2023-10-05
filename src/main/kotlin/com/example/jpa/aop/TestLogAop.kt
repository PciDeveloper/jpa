package com.example.jpa.aop

import com.example.jpa.entity.LogEntity
import com.example.jpa.repository.LogEntryRepository
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.*

@Aspect
@Component
class TestLogAop {

    @Autowired
    private lateinit var logEntryRepository: LogEntryRepository

    private val logger = LoggerFactory.getLogger(TestLogAop::class.java)

    // com.example.jpa.controller 이하 패키지의 모든 클래스 이하 모든 메서드에 적용
    // execution => 접근제어자
    @Pointcut("execution(* com.example.jpa.controller..*.*(..))")
    private fun cut() {}

    // Pointcut 에 의해 필터링된 경로로 들어오는 경우 메서드 호출 '전'에 적용
    // JoinPoint 객체는 각 컨트롤러에 정의된 method들의 args(매개변수)정보를 담고 있는 객체
    @Before("cut()")
    fun beforeParameterLog(joinPoint: JoinPoint) {

        // 메서드 정보 받아오기
        val method = getMethod(joinPoint)
        logger.info("============ method name : {} ============", method.name)

        // 파라미터 받아오기
        val args = joinPoint.args
        if (args.isEmpty()) logger.info("no parameter")
        for (arg in args) {
            logger.info("parameter type : {}", arg::class.simpleName)
            logger.info("parameter value : {}", arg)
        }
    }

    // Poincut에 의해 필터링된 경로로 들어오는 경우 메서드 리턴 '후'에 적용
    @AfterReturning(value = "cut()", returning = "result")
    fun logMethodCall(joinPoint: JoinPoint, result: Any?) {

        // 메서드 정보 받아오기
        val method = getMethod(joinPoint)
        logger.info("============ method name : {} ============", method.name)

        result?.let {
            logger.info("return type : {}", it::class.simpleName)
            logger.info("return value : {}", it)
        }

        // 로그 결과 값 logResult 변수에 할당
        val logResult = "Method called with result: $result"

        // LogEntity 엔티티를 생성하여 데이터베이스에 저장
        val logEntry = LogEntity(logResult = logResult, logDate = Date())
        logEntryRepository.save(logEntry)
    }

    // JoinPoint 로 메서드 정보 가져오기
    private fun getMethod(joinPoint: JoinPoint): Method {
        val signature = joinPoint.signature as MethodSignature
        return signature.method
    }
}