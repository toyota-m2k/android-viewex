plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.application)
}

android {
    namespace = "io.github.toyota32k.viewex"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.toyota32k.viewex"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
}

dependencies {
    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintLayout)
    implementation(libs.lifecycleExtensions)
    implementation(libs.lifecycleRuntimeKtx)
    implementation(libs.lifecycleLiveDataKtx)
    implementation(libs.lifecycleViewModelKtx)
    implementation(libs.android.utilities)
    implementation(libs.android.binding)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.espressoCore)
    implementation(project(":library"))
}