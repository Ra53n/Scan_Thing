import extensions.applyDefaultRepos

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.dagger.hilt.android") version "2.52" apply false
    kotlin("plugin.serialization") version "1.9.0" apply false
}

allprojects {
    repositories.applyDefaultRepos()
}