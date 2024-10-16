dependencies {
    api(project(":application"))
    api(project(":infrastructure"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks {
    jar {
        manifest {
            attributes("Main-Class" to "com.hanghae.project.webapi.Application")
        }
    }
}

tasks.getByName("bootJar") {
    enabled = true
}

springBoot {
    mainClass.set("com.hanghae.project.webapi.Application")
    buildInfo()
}