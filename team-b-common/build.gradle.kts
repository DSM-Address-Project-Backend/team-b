plugins {
    kotlin("plugin.jpa") version "1.6.21"
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("plugin.spring") version "1.6.21"
}

dependencies {
    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
