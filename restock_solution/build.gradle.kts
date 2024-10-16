plugins {
	java
	`java-library`
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

allprojects {
	group = "com.hanghae"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "java-library")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("ch.qos.logback:logback-classic")
	}

	tasks.getByName("bootJar") {
		enabled = false
	}

	tasks {
		register("prepareKotlinBuildScriptModel"){}

		jar {
			enabled = true
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}