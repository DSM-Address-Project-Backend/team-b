plugins {
    id("org.springframework.boot") version "3.1.4"
    kotlin("plugin.spring") version "1.6.21"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("plugin.jpa") version "1.6.21"
}

dependencies {
    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MySQL
    runtimeOnly("mysql:mysql-connector-java")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

allOpen {
    annotation("javax.persistence.Entity")
}

noArg {
    annotation("javax.persistence.Entity")
}
