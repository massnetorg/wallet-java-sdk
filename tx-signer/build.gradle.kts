import com.google.protobuf.gradle.*

plugins {
    kotlin("jvm")
    java
    idea
    id("com.google.protobuf") version "0.8.13"
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.13.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.bitcoinj:bitcoinj-core:0.15.7")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.0.0"
    }
}

kotlin {
    sourceSets {
        main {
        }
    }
}
