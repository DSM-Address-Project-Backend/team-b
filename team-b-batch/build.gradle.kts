plugins {
    id("org.springframework.boot") version "3.1.4"
    kotlin("plugin.spring") version "1.6.21"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
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

    implementation(project(":team-b-common"))
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
