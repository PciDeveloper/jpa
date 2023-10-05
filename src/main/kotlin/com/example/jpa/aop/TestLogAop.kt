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

    // LogEntryRepository 의존성 주입
    @Autowired
    private lateinit var logEntryRepository: LogEntryRepository

    // 로깅을 위한 Logger 객체 생성
    private val logger = LoggerFactory.getLogger(TestLogAop::class.java)

    // com.example.jpa.controller 이하 패키지의 모든 클래스 이하 모든 메서드에 적용할 Pointcut 정의, execution 접근제어
    // 포인트컷은 어떤 결합 지점에서 어떤 어드바이스를 실행할 것인지를 결정
    @Pointcut("execution(* com.example.jpa.controller..*.*(..))")
    private fun cut() {}

    // Pointcut 에 의해 필터링된 경로로 들어오는 경우 메서드 호출 '전'에 적용
    // JoinPoint 객체는 각 컨트롤러에 정의된 method들의 args(매개변수)정보를 담고 있는 객체이고,
    // 코드 실행 중에 관심사가 적용될 수 있는 지점을 가리킨다.
    // 예를 들어, 메서드 호출, 객체 생성, 필드 접근 등이 결합 지점이 예가 된다.
    @Before("cut()")
    fun beforeParameterLog(joinPoint: JoinPoint) {

        // 메서드 정보 받아오기
        val method = getMethod(joinPoint)
        logger.info("============ Before method name : {} ============", method.name)

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
        logger.info("============ After method name : {} ============", method.name)

        // 메서드의 리턴 타입 및 리턴 값 로깅
        result?.let {
            logger.info("return type : {}", it::class.simpleName)
            logger.info("return value : {}", it)
        }

        // 로그 결과 값 logResult 변수에 할당
        val logResult = "result: $result"

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