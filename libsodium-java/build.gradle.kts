plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "org.rivchain.libsodium_java"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        // Disable checking for API level issues
        lintConfig = file("lint.xml")
    }
}

dependencies {

    implementation("net.java.dev.jna:jna:5.18.0@aar")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("com.goterl:resource-loader:2.1.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:1.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}