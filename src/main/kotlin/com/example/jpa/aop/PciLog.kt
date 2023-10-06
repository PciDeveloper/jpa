package com.example.jpa.aop

// aop 사용 3 : annotation class 생성. 단, MyAspectTime.kt 에 있는 @Around 에서 명시한 annotation 이름과 일치 시켜야한다.
// MyAspectTime 에서 @Around 로 지정한 annotation 이름과 일치시키기 LogMyAspectTime
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PciLog