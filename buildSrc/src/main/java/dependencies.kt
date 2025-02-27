package com.rokt.roktdemo.buildsrc

object Versions {
    const val ktlint = "0.40.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:8.0.2"
    const val googleMaterial = "com.google.android.material:material:1.3.0"
    const val rokt = "com.rokt:roktsdk:4.8.1"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val okhttp = "com.squareup.okhttp3:okhttp:4.9.0"
    const val coil = "io.coil-kt:coil-compose:2.2.0"
    const val mlkitQRScanner = "com.google.android.gms:play-services-code-scanner:16.1.0"

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"

    }

    object Kotlin {
        private const val version = "1.8.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Coroutines {
        private const val version = "1.4.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Accompanist {
        private const val version = "0.12.0"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.5.0-beta03"
        const val appcompat = "androidx.appcompat:appcompat:1.2.0-rc01"
        const val navigation = "androidx.navigation:navigation-ui-ktx:2.3.4"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val lifeCycle = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        const val splashScreen = "androidx.core:core-splashscreen:1.0.0-beta01"

        object Compose {
            private const val version = "1.4.7"

            const val foundation = "androidx.compose.foundation:foundation:1.4.3"
            const val layout = "androidx.compose.foundation:foundation-layout:1.4.3"
            const val ui = "androidx.compose.ui:ui:1.4.3"
            const val uiUtil = "androidx.compose.ui:ui-util:1.4.3"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val material = "androidx.compose.material:material:1.4.3"
            const val material3 = "androidx.compose.material3:material3:1.1.2"
            const val animation = "androidx.compose.animation:animation:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:1.4.3"
            const val navigation = "androidx.navigation:navigation-compose:1.0.0-alpha08"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.0-alpha04"
        }

        object Lifecycle {
            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha03"
        }
    }

    object Hilt {
        private const val version = "2.44.2"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val testing = "com.google.dagger:hilt-android-testing:$version"
    }

    object Test {
        private const val version = "1.3.0"
        const val core = "androidx.test:core:$version"
        const val rules = "androidx.test:rules:$version"
        const val mockk = "io.mockk:mockk:1.11.0"
        const val truth = "com.google.truth:truth:1.1.2"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3"

        object Ext {
            private const val version = "1.1.2"
            const val junit = "androidx.test.ext:junit-ktx:$version"
        }
    }
}
