plugins {
    kotlin("js") version "1.3.70-eap-42" apply false
}

group = "srudie.diceroller"
version = "0.1-SNAPSHOT"

allprojects {
    repositories {
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        mavenCentral()
    }
}

//dependencies {
//    implementation(project(":engine"))
//
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
//    testCompile("junit:junit:4.12")
//
//    testImplementation("org.jetbrains.kotlin:kotlin-test-js")
//}

//kotlin.target.browser { }

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "5.4.1"
    }
}
