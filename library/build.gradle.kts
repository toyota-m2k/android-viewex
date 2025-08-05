import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.library)
    id("maven-publish")
}

android {
    namespace = "io.github.toyota32k.viewex.library"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.espressoCore)
}

// ./gradlew publishToMavenLocal
publishing {
    publications {
        // Creates a Maven publication called "release".
        register<MavenPublication>("release") {
            // You can then customize attributes of the publication as shown below.
            groupId = "com.github.toyota-m2k"
            artifactId = "android-viewex"
            version = project.findProperty("githubReleaseTag") as String? ?: "LOCAL"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/toyota-m2k/android-viewex")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}
