plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("plugin.spring") version "1.6.21"
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.2")
    }
}

dependencies {
    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Open Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // Batch
    implementation("org.springframework.batch:spring-batch-core")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MySQL
    implementation("mysql:mysql-connector-java:8.0.33")

    implementation(project(":team-b-common"))
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
