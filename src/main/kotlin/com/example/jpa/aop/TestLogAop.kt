package com.example.jpa.aop

import com.example.jpa.entity.LogEntity
import com.example.jpa.repository.LogEntryRepository
import com.google.gson.Gson
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.*

@Aspect
@Component
class TestLogAop {

    @Autowired
    private lateinit var logEntryRepository: LogEntryRepository // LogEntryRepository 의존성 주입

    // 로깅을 위한 Logger 객체 생성
    private val logger = LoggerFactory.getLogger(TestLogAop::class.java)

    // com.example.jpa.controller 이하 패키지내의 모든 클래스, 모든 메서드에 로깅을 적용할 포인트컷(Pointcut) 정의, execution 접근제어
    // 포인트컷(@Pintcut)은 어떤 결합 지점에서 어떤 어드바이스를 실행할 것인지를 결정
    // 즉 어디에 적용해야 하는지에 대한 정보를 담고 있다.
    // @Pointcut 에 등록되어 있는 cut 메서드를 @Before 에서 포인트컷 cut() 을 참조하여 beforeParameterLog 메서드를 실행시키려는 역할을 한다.
    @Pointcut("execution(* com.example.jpa.controller..*.*(..))")
    private fun cut() {}

    // 위의 @Pointcut(포인트컷) 에 이어서 @Before 에서 cut 메서드를 참조하여 beforeParameterLog 해당 메서드가 실행될 때
    // 포인트컷에서 지정한 해당 패키지의 controller 에서 호출한 메서드의 정보들은 JoinPoint 에 전달된다.
    // 즉, JoinPoint 객체는 각 컨트롤러에 정의된 method들의 args(매개변수)정보를 담고 있는 객체이다.
    // Pointcut 에 의해 필터링된 경로로 들어오는 경우 메서드 호출 '전'에 적용
    @Before("cut()")
    fun beforeParameterLog(joinPoint: JoinPoint) {

        // 메서드 정보 받아오기
        val method = getMethod(joinPoint)
        logger.info("============ @Before method name : {} ============", method.name)

        // 파라미터 받아오기
        val args = joinPoint.args
        if (args.isEmpty()) logger.info("no parameter")
        for (arg in args) {
            logger.info("파라미터 타입 : {}", arg::class.simpleName)
            logger.info("파라미터 값 : {}", arg)
        }
    }

    // Poincut에 의해 필터링된 경로로 들어오는 경우 메서드 리턴 '후'에 적용
    // @AfterReturning => 즉, 발동된 메서드가 실행된 후에 실행되는 Aspect 로,
    // 메서드가 반환한 결과를 로깅하고 DB 에 저장하는 역할을 한다.
    // 여기서의 value cut() 은 메서드 호출이 끝난 후에 logMethodCall 메서드가 실행되도록 지시한다.
    // returning 속성은 메서드의 반환값들을 어떤 이름으로 가져올 것인지 작명
    @AfterReturning(value = "cut()", returning = "result")
    fun logMethodCall(joinPoint: JoinPoint, result: Any?) {

        val gson = Gson()

        // 메서드 정보 받아오기
        val method = getMethod(joinPoint)
        logger.info("============ @AfterReturning method name : {} ============", method.name)

        // Request 요청 데이터 로깅
        val req = getRequestData(joinPoint)
        logger.info("============ [Request] : {} ============", req)

        // 메서드의 리턴 타입 및 리턴 값 로깅
        // 메서드가 반환한 결과("result") 가 null 이 아닌 경우에만 실행되는 블록이다.
        result?.let {
            logger.info("return type : {}", it::class.simpleName)
            logger.info("return value : {}", it)
        }

        // result 결과 값을 Json 으로 변환
        val logResultJson = gson.toJson(result)

        val reqJson = gson.toJson(req)

        // LogEntity 엔티티를 생성하여 각각의 필드에 값을 할당하고 데이터베이스에 저장한다.
        val logEntry = LogEntity(logResult = logResultJson, req = reqJson, logDate = Date())
        logEntryRepository.save(logEntry)
    }

    // 이 페이지에 어노테이션을 가지고 있는 모든 메서드들에서 getMethod 를 호출하여 joinPoint 를 사용할 수 있게 메서드 작성 (시발점)
    // JoinPoint 로 메서드 정보 가져오기
    // joinPoint.signature 속성은 해당 메서드의 시그니처 정보를 제공한다.
    // 이어서 joinPoint.signature 을 as 키워드를 통하여 MethodSignature 타입으로 형변환 한다.
    // signature.method 는 형변환된 MethodSignature 에서 method 속성을 사용하여 현재 실행중인 메서드를 가져오기 위함.
    private fun getMethod(joinPoint: JoinPoint): Method {
        val signature = joinPoint.signature as MethodSignature
        return signature.method
    }

    // Request 요청 데이터를 가져오는 메서드
    // Request 로그를 사용하려면 해당 컨트롤러 메서드에 request: HttpServletRequest 매개변수 세팅
    private fun getRequestData(joinPoint: JoinPoint): Any {

        //  HTTP 요청 객체를 찾기 위해 find 함수를 사용
        val request = joinPoint.args.find { it is HttpServletRequest }

        if (request is HttpServletRequest) {
            val host = request.getHeader("Host") // localhost:8080
            val requestURL = request.requestURL // http://localhost:8080/save
            val requestURI = request.requestURI // 요청 URI /save
            val method = request.method // 요청 메서드 Get, Post

            val requestData = mapOf(
                "host" to host,
                "requestURL" to requestURL,
                "requestURI" to requestURI,
                "method" to method
            )

            // 요청이 들어왔을 때 host, requestURL, requestURI, method 값 던지기
            return ResponseEntity.ok(hashMapOf("mode" to true, "data" to requestData))
        }
        return "요청 데이터 없음"
    }

}