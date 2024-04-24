import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"

	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("plugin.jpa") version "1.9.23"

	jacoco
}

group = "com.tikiticket"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
	testImplementation("io.mockk:mockk:1.13.5")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}


jacoco {
	// JaCoCo 버전
	toolVersion = "0.8.12"

//  테스트결과 리포트를 저장할 경로 변경
//  default는 "${project.reporting.baseDir}/jacoco"
//  reportsDir = file("$buildDir/customJacocoReportDir")
}

// 리포트 형식 지정
tasks.jacocoTestReport {
	reports {
		html.required.set(true)
		xml.required.set(false)
		csv.required.set(false)
	}

	finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			element = "CLASS"

			// 라인 커버리지 제한
//			limit {
//				counter = "LINE"
//				value = "COVEREDRATIO"
//				minimum = "0.80".toBigDecimal()
//			}

			// 브랜치 커버리지 제한
			limit {
				counter = "BRANCH"
				value = "COVEREDRATIO"
				minimum = "0.90".toBigDecimal()
			}

			// 빈 줄을 제외한 코드의 라인수를 최대 200라인으로 제한
			limit {
				counter = "LINE"
				value = "TOTALCOUNT"
				maximum = "200".toBigDecimal()
			}

			// excludes = []
		}
	}
}