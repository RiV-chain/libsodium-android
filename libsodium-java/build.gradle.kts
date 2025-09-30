plugins {
    `java-library`
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

// Create custom configuration for native libraries
val nativeLibs by configurations.creating

dependencies {
    implementation("net.java.dev.jna:jna:5.18.0")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("com.goterl:resource-loader:2.1.0")

    // Add the AAR dependency to our custom configuration
    nativeLibs("org.libsodium:libsodium:1.0.21.0@aar")

    testImplementation(libs.junit)
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:1.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}

// Task to extract native libraries from AAR and organize them in proper structure
val extractAar by tasks.registering {
    doLast {
        val aarFiles = configurations["nativeLibs"].resolve()
        if (aarFiles.isNotEmpty()) {
            val aarFile = aarFiles.first()
            val extractedDir = layout.buildDirectory.dir("extracted-aar").get().asFile
            extractedDir.mkdirs()

            // Extract the AAR file
            copy {
                from(zipTree(aarFile))
                into(extractedDir)
            }

            // Create the proper directory structure in test resources
            val testResourcesDir = File(sourceSets["test"].resources.srcDirs.first().toString())
            testResourcesDir.mkdirs()

            // Map AAR JNI folders to expected resource structure
            val jniDir = File(extractedDir, "jni")
            if (jniDir.exists()) {
                jniDir.listFiles()?.forEach { abiDir ->
                    if (abiDir.isDirectory) {
                        val targetDirName = when {
                            abiDir.name.startsWith("arm64") -> "arm64"
                            abiDir.name.startsWith("armeabi-v7a") -> "armv6"
                            abiDir.name.startsWith("x86_64") -> "linux64"
                            abiDir.name.startsWith("x86") -> "linux"
                            abiDir.name.startsWith("linux") -> abiDir.name
                            else -> abiDir.name
                        }

                        val targetDir = File(testResourcesDir, targetDirName)
                        targetDir.mkdirs()

                        // Copy library files (keeping original names)
                        abiDir.listFiles()?.forEach { libFile ->
                            if (libFile.name.endsWith(".so") ||
                                libFile.name.endsWith(".dll") ||
                                libFile.name.endsWith(".dylib")) {
                                copy {
                                    from(libFile)
                                    into(targetDir)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Configure testing with native library support
tasks.test {
    dependsOn(extractAar)
    useJUnitPlatform()

    // Enable test logging
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }

    // Set JVM arguments for tests if needed
    jvmArgs("-Xmx2g")
}

// Configure integration tests with native library support
tasks.register<Test>("integrationTest") {
    dependsOn(extractAar)
    description = "Runs integration tests."
    group = "verification"
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    useJUnitPlatform()

    // Fail the build if any test fails
    finalizedBy("check")

    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

// Configure source sets for integration tests
sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

// Publishing configuration
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.rivchain"
            artifactId = "libsodium-java"
            version = "2.0.0"
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}

// Add this to ensure tests run during build
tasks.check {
    dependsOn(tasks.test)
}