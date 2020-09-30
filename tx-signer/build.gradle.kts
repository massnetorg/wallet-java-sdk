import com.google.protobuf.gradle.*

plugins {
    kotlin("jvm")
    java
    idea
    id("com.google.protobuf") version "0.8.13"
    `maven-publish`
    signing
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

publishing {
    publications {
        create<MavenPublication>("txSigner") {
            groupId = Constants.artifactGroup
            artifactId = project.name
            version = "0.3.3"
            from(components["java"])

            pom {
                name.set(project.name)
                description.set("MassNet Wallet SDK (offline transaction signer)")
                url.set(Constants.pomUrl)
                licenses {
                    license {
                        name.set(Constants.pomLicenseName)
                        url.set(Constants.pomLicenseUrl)
                        distribution.set(Constants.pomLicenseDist)
                    }
                }
                developers {
                    developer {
                        id.set(Constants.pomDeveloperId)
                        name.set(Constants.pomDeveloperName)
                    }
                }
                scm {
                    connection.set(Constants.pomScmConnection)
                    url.set(Constants.pomScmUrl)
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["txSigner"])
}
