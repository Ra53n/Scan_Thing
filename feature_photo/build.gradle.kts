plugins {
    id("android_library")
}

dependencies {
    implementation(libs.hilt)
    kapt (libs.hilt.compiler)

    implementation(libs.camera.core)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.camera2)
    implementation(libs.camera.video)
    implementation(libs.camera.view)
    implementation(libs.camera.extensions)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)

    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
}

android {
    namespace = "ra53n.scan_thing.feature_photo"
}