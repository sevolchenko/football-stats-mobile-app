@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "ru.vsu.mobile.footballstats"
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.vsu.mobile.footballstats"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}


object Versions {
    const val thrifty_version = "2.1.2"
    const val retrofit_version = "2.9.0"
    const val okhttp_version = "4.9.1"
    const val okhttp_interceptor_version = "4.7.2"
    const val okio_version = "2.10.0"
    const val picasso_version = "2.71828"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Retrofit components
    api("com.squareup.retrofit2:retrofit:${Versions.retrofit_version}")
    api("com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}")
    // Okhttp components
    api("com.squareup.okhttp3:okhttp:${Versions.okhttp_version}")
    api("com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_interceptor_version}")
    // Picasso components
    implementation("com.squareup.picasso:picasso:${Versions.picasso_version}")

    implementation("io.coil-kt:coil:2.2.2")
    implementation("io.coil-kt:coil-svg:2.2.2")
}