dependencies {
    api("org.springframework:spring-tx")
    implementation("com.google.guava:guava:32.1.3-jre")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
