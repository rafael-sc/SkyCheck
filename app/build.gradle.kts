plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.example.skycheck"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.example.skycheck"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "\"${System.getenv("API_KEY")}\"")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
            excludes += "/META-INF/*"
        }
    }
}

dependencies {
    // Google Location
    implementation(libs.play.services.location)
    testImplementation(libs.junit.junit)

    // Room Database
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Logging Interceptor e OkHttpClient
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Retrofit
    implementation(libs.retrofit)

    // GSON
    implementation(libs.gson)
    implementation(libs.converter.gson)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // DataStore
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    // Material Icons extended
    implementation(libs.androidx.material.icons.extended)

    // Serialization
    implementation(libs.kotlin.serialization)

    // MockK
    implementation(libs.mockk)

    // Test - aditional libs
    androidTestImplementation(libs.core.testing)
    testImplementation(libs.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.runner)
    testImplementation(libs.androidx.core)
    testImplementation(libs.robolectric)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}