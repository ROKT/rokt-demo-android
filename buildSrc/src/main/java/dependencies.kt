package com.rokt.roktdemo.buildsrc

object Versions {
    const val ktlint = "0.40.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0-alpha10"
    const val googleMaterial = "com.google.android.material:material:1.3.0"

    object Kotlin {
        private const val version = "1.4.31"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Coroutines {
        private const val version = "1.4.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.5.0-beta03"
        const val appcompat = "androidx.appcompat:appcompat:1.2.0-rc01"

        object Compose {
            const val version = "1.0.0-beta02"

            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val material = "androidx.compose.material:material:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.0-alpha04"
        }

        object Lifecycle {
            const val viewModelCompose =
            "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha03"
        }
    }

    object Test {
        private const val version = "1.3.0"
        const val core = "androidx.test:core:$version"
        const val rules = "androidx.test:rules:$version"

        object Ext {
            private const val version = "1.1.2"
            const val junit = "androidx.test.ext:junit-ktx:$version"
        }
    }
}