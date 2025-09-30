plugins {
    id("com.android.library")
    id("io.deepmedia.tools.grease") version "0.3.7"
    id("maven-publish")
}

android {
    namespace = "org.rivchain.libsodium_android"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    // This is important for publishing Android libraries
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    grease("org.rivchain:libsodium-java:2.0.0")
    grease("org.libsodium:libsodium:1.0.21.0@aar")
    implementation("net.java.dev.jna:jna:5.18.0@aar")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            groupId = "org.rivchain"
            artifactId = "libsodium-android"
            version = "2.0.0"

            // Use the release component that we defined above
            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        mavenLocal()
    }
}