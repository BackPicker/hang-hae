dependencies {
    api(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("org.springframework.boot:spring-boot-autoconfigure")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
