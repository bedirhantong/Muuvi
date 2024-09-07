object Dependencies {
    object AndroidX {
        val coreKtx by lazy { "androidx.core:core-ktx:" + Versions.coreKtx }
        val appCompat by lazy { "androidx.appcompat:appcompat:" + Versions.appcompat }
        val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:" + Versions.constraintlayout }
        val activity by lazy {  "androidx.activity:activity:"+ Versions.activity }
    }

    object Test {
        val junit by lazy { "junit:junit:" + Versions.junit }
        val testExt by lazy { "androidx.test.ext:junit:" + Versions.junitVersion }
        val espresso by lazy { "androidx.test.espresso:espresso-core:" + Versions.espressoCore }
    }

    object Google {
        val material by lazy { "com.google.android.material:material:" + Versions.material }
    }

    object Retrofit {
        val gson by lazy { "com.squareup.retrofit2:converter-gson:" + Versions.gson }
        val retrofit by lazy { "com.squareup.retrofit2:retrofit:" + Versions.retrofit }
    }

    object DependencyInjection {
        val hilt by lazy { "com.google.dagger:hilt-android:" + Versions.hiltVersion }
        val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:" + Versions.hiltVersion }
    }

    object Navigation {
        val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:" + Versions.navVersion }
        val navigationUi by lazy { "androidx.navigation:navigation-ui-ktx:" + Versions.navVersion }
    }

    object Coroutines {
        val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:" + Versions.coroutines }
        val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:" + Versions.coroutines }
        val gsonCoroutines by lazy { "com.google.code.gson:gson:" + Versions.gsonCoroutines }
    }

    object Lifecycle {
        val viewmodel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:" + Versions.lifecycleVersion }
        val livedata by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:" + Versions.lifecycleVersion }
    }

    object Glide {
        val glide by lazy { "com.github.bumptech.glide:glide:" + Versions.glide }
        val glideCompiler by lazy { "com.github.bumptech.glide:compiler:" + Versions.glide }
    }

    object ViewPager {
        val viewpager by lazy { "androidx.viewpager2:viewpager2:" + Versions.viewpager }
    }

    object Indicator {
        val circularIndicator by lazy { "me.relex:circleindicator:" + Versions.circularIndicator }
    }

    object Effect {
        val shimmerEffect by lazy { "com.facebook.shimmer:shimmer:" + Versions.shimmer }
    }

    object Splash {
        val splashAnimation by lazy { "androidx.core:core-splashscreen:" + Versions.splashScreen }
    }

    object SecretGradle {
        val secretGradle by lazy { "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:"+Versions.secretGradle }
        val safeArgs by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:"+Versions.navVersion }
    }
}
