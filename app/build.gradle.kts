import com.github.triplet.gradle.androidpublisher.ReleaseStatus

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("maven-publish")
    id("com.github.triplet.play") version "3.11.0"
}

android {
    namespace = "ra53n.scan_thing"
    compileSdk = 34

    defaultConfig {
        applicationId = "ra53n.scan_thing"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = false
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    publishing {
        singleVariant("release") {
            publishApk()
        }
    }
}

// Примерный код публикации в Google Play
play {
    enabled.set(false)
    track.set("release")
    userFraction.set(0.10)
    defaultToAppBundles.set(true)
    releaseStatus.set(ReleaseStatus.IN_PROGRESS)
    serviceAccountCredentials.set(file("google-play.json"))
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "ra53n.scan_thing"
            artifactId = "app"
            version = "1.0"
            afterEvaluate {
                from(components["release"])
            }
        }
        repositories {
            maven {
                name = "publishing"
                url = uri(layout.buildDirectory.dir("repo"))
            }
        }
    }
}

tasks.register<Zip>("generateRepo") {
    val publishTask = tasks.named(
        "publishReleasePublicationToPublishingRepository",
        PublishToMavenRepository::class.java
    )
    from(publishTask.map { it.repository.url })
    into("release")
    archiveFileName.set("scan_thing_release.zip")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature_main"))
    implementation(project(":feature_photo"))
    implementation(project(":feature_scan_photo"))

    implementation(libs.hilt)
    implementation(libs.androidx.rules)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    implementation(libs.coil)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.camera.core)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.camera2)
    implementation(libs.camera.video)
    implementation(libs.camera.view)
    implementation(libs.camera.extensions)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation("androidx.test:core-ktx:1.6.1")
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}