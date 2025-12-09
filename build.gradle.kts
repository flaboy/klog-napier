plugins {
    kotlin("multiplatform") version "2.2.21"
    id("com.android.library") version "8.7.3"
}

group = "com.flaboy"
version = "1.0.0-SNAPSHOT"

kotlin {
    androidTarget()
    jvm()
    iosArm64()
    iosSimulatorArm64()

    // Desktop Native targets
    macosArm64()
    macosX64()
    linuxX64()
    mingwX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("com.flaboy:klog:1.0.0-SNAPSHOT")
                api("io.github.aakira:napier:2.7.1")
            }
        }
        
        // Native targets share common implementation
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        
        val macosArm64Main by getting {
            dependsOn(nativeMain)
        }
        
        val macosX64Main by getting {
            dependsOn(nativeMain)
        }
        
        val linuxX64Main by getting {
            dependsOn(nativeMain)
        }
        
        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }
    }
}

android {
    namespace = "com.flaboy.klog.napier"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

