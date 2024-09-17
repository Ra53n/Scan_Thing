import java.util.Properties

plugins {
    id("android_library")
}

dependencies {
    implementation(project(":core"))

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    implementation(libs.coil)

    implementation(libs.hilt)
    kapt (libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.gson)

    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
}
android {
    namespace = "ra53n.scan_thing.feature_scan_photo"

    defaultConfig {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }

        buildConfigField("String", "API_KEY", "\"${localProperties["API_KEY"]}\"")
    }
}