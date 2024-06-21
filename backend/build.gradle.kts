plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-websocket:3.3.0")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    // Spring Boot Starter Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Boot Starter Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Spring Security OAuth2 Resource Server
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // Nimbus JOSE JWT for JWT decoding
    implementation("com.nimbusds:nimbus-jose-jwt")

    // Spring Security Messaging for WebSocket security
    implementation("org.springframework.security:spring-security-messaging")

    // Jackson Module Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Kotlin Reflect and Stdlib
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")

    // JUnit Platform Launcher
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
