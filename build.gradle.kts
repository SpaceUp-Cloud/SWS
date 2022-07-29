/*
 * Copyright(c) 2022 thraax.session@gino-atlas.de.
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
    kotlin("jvm") version "1.7.0"
    id("org.sonarqube") version "3.3"
    id("maven-publish")
    id("java")
    application
    jacoco
}

apply<MavenPublishPlugin>()

group = "technology.iatlas.sws"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.1.0")
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation(kotlin("test"))
}

jacoco {
    toolVersion = "0.8.7"
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