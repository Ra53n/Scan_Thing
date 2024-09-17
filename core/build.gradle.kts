plugins {
    id("android_library")
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.compose.ui)
    implementation(platform(libs.compose.bom))

    implementation(libs.hilt)
    kapt (libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
}

android {
    namespace = "ra53n.scan_thing.core"
}