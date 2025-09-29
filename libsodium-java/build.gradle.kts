plugins {
    `java-library`
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("net.java.dev.jna:jna:5.18.0")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("com.goterl:resource-loader:2.1.0")
    testImplementation(libs.junit)
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:1.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}