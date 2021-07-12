import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm") version "1.5.20"
    `maven-publish`
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.gradle.maven-publish")

    dependencies {
        val junitVersion = "5.6.2"

        implementation(kotlin("stdlib-jdk8"))
        implementation("com.google.code.gson:gson:2.8.6")
        testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    }

    tasks {
        compileKotlin {
            kotlinOptions.jvmTarget = "1.8"
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = "1.8"
        }
        test {
            useJUnitPlatform()
            testLogging {
                exceptionFormat = TestExceptionFormat.FULL
                events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
            }
        }
    }

    java {
        withJavadocJar()
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    publishing {
        repositories {
            maven {
                val snapshotUrl = uri("https://oss.sonatype.org/content/repositories/snapshots")
                val releaseUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotUrl else releaseUrl
                credentials {
                    val mavenUsername: String by project
                    val mavenPassword: String by project
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        }
    }
}
