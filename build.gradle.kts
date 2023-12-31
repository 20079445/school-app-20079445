plugins {
    kotlin("jvm") version "1.9.0"
    application
    jacoco
}

group = "ie.setu.classes"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // dependencies for logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    //dependencies for persistence
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")
    //dependencies for UI
    implementation("com.googlecode.lanterna:lanterna:3.1.1")
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

jacoco {
    applyTo(tasks.run.get())
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.register<JacocoReport>("applicationCodeCoverageReport") {
    executionData(tasks.run.get())
    sourceSets(sourceSets.main.get())
}



