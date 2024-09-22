plugins {
    id("android_library")
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.material3)
    implementation(platform(libs.compose.bom))
}

android {
    namespace = "ra53n.scan_thing.ui_kit"
}