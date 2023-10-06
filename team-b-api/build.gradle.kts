plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("plugin.spring") version "1.6.21"
}

dependencies {
    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // impl project
    implementation(project(":team-b-common"))
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
