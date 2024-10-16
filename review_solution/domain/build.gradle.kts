dependencies {
    implementation("org.springframework:spring-tx")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
