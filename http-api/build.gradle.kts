plugins {
    kotlin("jvm")
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
