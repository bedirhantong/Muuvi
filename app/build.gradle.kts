plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.bedirhan.muuvi"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("boolean", "DEBUG", "true")

            // Use the proguard file
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Act as a release build
            isMinifyEnabled = true
            isDebuggable = true
            isShrinkResources = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

}

dependencies {
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.Google.material)

    // Test
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.testExt)
    androidTestImplementation(Dependencies.Test.espresso)


    // Dependency Injection - Hilt
    implementation(Dependencies.DependencyInjection.hilt)
    kapt(Dependencies.DependencyInjection.hiltCompiler)

    // Navigation
    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)

    // gson library
    implementation(Dependencies.Retrofit.gson)
    // coroutines
    implementation(Dependencies.Coroutines.coroutines)
    implementation(Dependencies.Coroutines.coroutinesCore)
    implementation(Dependencies.Coroutines.gsonCoroutines)

    // retrofit
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.gson)

    // viewmodel and livedata
    implementation(Dependencies.Lifecycle.viewmodel)
    implementation(Dependencies.Lifecycle.livedata)

    //glide
    implementation(Dependencies.Glide.glide)
    kapt(Dependencies.Glide.glideCompiler)

    // viewpager
    implementation(Dependencies.ViewPager.viewpager)

    // circular indicator
    implementation(Dependencies.Indicator.circularIndicator)

    implementation(Dependencies.Effect.shimmerEffect)

    // animated splash screen
    implementation(Dependencies.Splash.splashAnimation)
}

// Hilt
// Allow references to generated code
kapt {
    correctErrorTypes = true
}