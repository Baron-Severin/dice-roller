
plugins {
    id("org.jetbrains.kotlin.js")
}

group = "org.srudie"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-js")
    testImplementation(npm("karma", "4.4.1"))
}

kotlin.target.browser { }
