plugins {
    kotlin("multiplatform") version "1.9.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm("klox_jvm") {
        jvmToolchain(17)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    js("klox_js") {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val isMingwX64 = hostOs.startsWith("Windows")
    val klox_nativeTarget = when {
        hostOs == "Mac OS X" && isArm64 -> macosArm64("klox_native")
        hostOs == "Mac OS X" && !isArm64 -> macosX64("klox_native")
        hostOs == "Linux" && isArm64 -> linuxArm64("klox_native")
        hostOs == "Linux" && !isArm64 -> linuxX64("klox_native")
        isMingwX64 -> mingwX64("klox_native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    
    sourceSets {
        val loggingVersion = "5.1.0"
        val commonMain by getting{
            dependencies{
                implementation("io.github.oshai:kotlin-logging:$loggingVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val klox_jvmMain by getting
        val klox_jvmTest by getting
        val klox_jsMain by getting
        val klox_jsTest by getting
        val klox_nativeMain by getting
        val klox_nativeTest by getting
    }
}
