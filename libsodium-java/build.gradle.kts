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
        try {
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

                // Get the test resources directory
                val testResourcesDir = File(sourceSets["test"].resources.srcDirs.first().toString())
                testResourcesDir.mkdirs()

                println("=== Initial test/resources content ===")
                printDirectoryTree(testResourcesDir, 0)

                // Process only x86_64 ABI for linux64
                val jniDir = File(extractedDir, "jni")
                if (jniDir.exists()) {
                    jniDir.listFiles()?.forEach { abiDir ->
                        if (abiDir.isDirectory && abiDir.name.startsWith("x86_64")) {
                            // Copy to linux64 directory only
                            val targetDir = File(testResourcesDir, "linux64")
                            targetDir.mkdirs()

                            // Copy all .so library files from the x86_64 ABI directory
                            abiDir.listFiles()?.forEach { libFile ->
                                if (libFile.name.endsWith(".so")) {
                                    copy {
                                        from(libFile)
                                        into(targetDir)
                                        // Rename to standard name
                                        rename { "libsodium.so" }
                                    }
                                }
                            }
                        }
                    }
                }

                println("=== Final test/resources content after extraction ===")
                printDirectoryTree(testResourcesDir, 0)
                println("Linux64 directory extracted to: ${testResourcesDir.absolutePath}")
            }
        } catch (e: Exception) {
            println("Warning: Could not extract native libraries: ${e.message}")
            e.printStackTrace()
        }
    }
}

// Helper function to print directory tree
fun printDirectoryTree(dir: File, depth: Int) {
    val indent = "  ".repeat(depth)
    if (dir.exists()) {
        println("${indent}${dir.name}/")
        if (dir.isDirectory) {
            dir.listFiles()?.sortedBy { it.name }?.forEach { file ->
                if (file.isDirectory) {
                    printDirectoryTree(file, depth + 1)
                } else {
                    println("${indent}  ${file.name}")
                }
            }
        }
    } else {
        println("${indent}${dir.name} (does not exist)")
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

    doFirst {
        val testResourcesDir = File(sourceSets["test"].resources.srcDirs.first().toString())
        println("=== Test resources available at runtime ===")
        printDirectoryTree(testResourcesDir, 0)
    }
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

    doFirst {
        val testResourcesDir = File(sourceSets["test"].resources.srcDirs.first().toString())
        println("=== Integration test resources ===")
        printDirectoryTree(testResourcesDir, 0)
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