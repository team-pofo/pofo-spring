import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    runtimeOnly("org.postgresql:postgresql:42.7.4")
    runtimeOnly("com.h2database:h2:2.3.232")
}