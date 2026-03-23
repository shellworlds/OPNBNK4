plugins {
    id("java-library")
}

group = "com.bank"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    api("com.fasterxml.jackson.core:jackson-annotations:2.21.2")
    api("com.fasterxml.jackson.core:jackson-databind:2.21.2")
}
