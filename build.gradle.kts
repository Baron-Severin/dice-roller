plugins {
    id("org.jetbrains.kotlin.js") version "1.3.72"
}

group = "org.srudie"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
    testCompile("junit:junit:4.12")

    testImplementation("org.jetbrains.kotlin:kotlin-test-js")
}

kotlin.target.browser { }
