plugins {
    kotlin("jvm")
    `maven-publish`
    signing
}

dependencies {
    val okhttpVersion = "4.9.0"
    val retrofitVersion = "2.9.0"

    implementation("io.reactivex.rxjava3:rxjava:3.0.5")
    implementation("com.squareup.okhttp3:okhttp:${okhttpVersion}")
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:adapter-rxjava3:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
}

publishing {
    publications {
        create<MavenPublication>("httpApi") {
            groupId = Constants.artifactGroup
            artifactId = project.name
            version = "0.3.2"
            from(components["java"])

            pom {
                name.set(project.name)
                description.set("MassNet Wallet SDK (full node wallet HTTP API wrapper)")
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
    sign(publishing.publications["httpApi"])
}
