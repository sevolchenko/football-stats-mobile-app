// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.application") version ("7.4.2") apply false
    id("com.android.library") version ("7.4.2") apply false
    id("org.jetbrains.kotlin.android") version ("1.8.0") apply false
}



buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        val navVersion = "2.5.3"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

true // Needed to make the Suppress annotation work for the plugins block