/*
 * Copyright(c) 2023 spaceup@iatlas.technology.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

plugins {
    kotlin("jvm") version "1.9.10"
    id("org.sonarqube") version "5.0.0.4638"
    id("maven-publish")
    id("java")
    application
    jacoco
    //java
}

apply<MavenPublishPlugin>()

group = "technology.iatlas.sws"
version = "1.5.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("ch.qos.logback:logback-core:1.4.11")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.junit.jupiter:junit-jupiter:5.10.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(11)
}

jacoco {
    toolVersion = "0.8.11"
    reportsDirectory.set(layout.buildDirectory.dir("jacoco"))
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.withType<PublishToMavenRepository>() {
    dependsOn("assemble")
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("jar") {
            group = project.group
            artifactId = artifactId
            version = version

            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "Maven"
            url = uri("https://artifactory.iatlas.dev/releases")
            credentials {
                username = project.properties["nexusUsername"].toString()
                password = project.properties["nexusPassword"].toString()
            }
        }
    }
}

sonarqube {
    properties {
        property("sonar.host.url", "https://sonar.iatlas.dev")
        property("sonar.login", project.properties["sonarToken"].toString())
        property("sonar.projectKey", "SpaceUp-SWS")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.java.coveragePlugin", "jacoco")
    }
}