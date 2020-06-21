
plugins {
    id("org.jetbrains.kotlin.js")
}

group = "org.srudie"
version = "0.1-SNAPSHOT"

dependencies {
    implementation(project(":core"))
    implementation(project(":engine"))
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
    implementation(npm("material-components-web"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-js")
    testImplementation(npm("karma", "4.4.1"))
}

kotlin.target.browser { }
