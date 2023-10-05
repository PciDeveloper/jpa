package com.example.jpa.aop

// aop 사용 2
// MyAspectTime 에서 @Around 로 지정한 annotation 이름과 일치시키기 LogMyAspectTime
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogExecutionTime