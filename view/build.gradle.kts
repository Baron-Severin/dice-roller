
plugins {
    id("org.jetbrains.kotlin.js")
}

group = "org.srudie"
version = "0.1-SNAPSHOT"

dependencies {
    implementation(project(":core"))
    implementation(project(":engine"))
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test-js")
    testImplementation(npm("karma", "4.4.1"))
//    testCompile(group = "io.mockk", name = "mockk", version = "1.10.0")

}

kotlin.target.browser { }