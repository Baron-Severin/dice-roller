plugins {
    kotlin("js") version "1.3.70-eap-42" apply false
}

group = "srudie.diceroller"
version = "0.1-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }
}
