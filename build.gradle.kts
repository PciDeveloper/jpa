import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"

	// mapStruct
	kotlin("kapt") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// MapStruct 의존성 추가
	implementation("org.mapstruct:mapstruct:1.5.1.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.1.Final")
	kaptTest("org.mapstruct:mapstruct-processor:1.5.1.Final")

	// @Valid 사용을 위한 의존성 추가
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// AOP 의존성 추가
	implementation("org.springframework.boot:spring-boot-starter-aop")

	// modelMapper
	implementation("org.modelmapper:modelmapper:2.4.3")

	// JSON
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

	// GSON
	implementation("com.google.code.gson:gson:2.8.9")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
	testImplementation("org.hamcrest:hamcrest:2.2")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
