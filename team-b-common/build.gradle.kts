plugins {
    kotlin("plugin.jpa") version "1.6.21"
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("plugin.spring") version "1.6.21"
}

dependencies {
    // MySQL
    implementation("mysql:mysql-connector-java:8.0.33")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // QueryDsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
}
