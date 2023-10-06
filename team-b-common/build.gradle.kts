plugins {
    id("org.springframework.boot") version "3.1.4"
    kotlin("plugin.spring") version "1.6.21"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("plugin.jpa") version "1.6.21"
}

dependencies {
    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MySQL
    runtimeOnly("mysql:mysql-connector-java")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

allOpen {
    annotation("jakarta.persistence.Entity")
}

noArg {
    annotation("jakarta.persistence.Entity")
}
