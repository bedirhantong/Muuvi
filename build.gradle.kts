// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Config.agp apply false
    id("org.jetbrains.kotlin.android") version Config.kotlin apply false
    id("com.google.dagger.hilt.android") version Versions.hiltVersion apply false
}
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(Dependencies.SecretGradle.secretGradle)
        classpath(Dependencies.SecretGradle.safeArgs)
    }
}

tasks.register("clean",Delete::class) {
    delete(rootProject.layout.buildDirectory)
}