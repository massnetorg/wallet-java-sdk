plugins {
    kotlin("jvm")
    `maven-publish`
    signing
}

dependencies {
    val okhttpVersion = "4.8.1"
    val retrofitVersion = "2.9.0"

    implementation("io.reactivex.rxjava3:rxjava:3.0.5")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.okhttp3:okhttp:${okhttpVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:${okhttpVersion}")
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:adapter-rxjava3:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("httpApi") {
            groupId = Constants.artifactGroup
            artifactId = project.name
            version = "0.1.0"
            from(components["java"])

            pom {
                name.set(project.name)
                description.set("MassNet Wallket SDK, wrapping the HTTP API of a MassNet wallet full node")
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

signing {
    useGpgCmd()
    sign(publishing.publications["httpApi"])
}
