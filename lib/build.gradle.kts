import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
//    id("io.kotest.multiplatform") version "5.9.0.1436-SNAPSHOT"
}

//apply(plugin = "io.kotest.multiplatform:5.9.0.1436-SNAPSHOT")
group = "com.github.sidky.graphik"

repositories {
    mavenCentral()
    google()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

kotlin {
    jvmToolchain(20)

    js {
        browser()
        nodejs()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs() {
        browser()
    }
    androidTarget()
//    iosArm64()
    jvm()

    applyHierarchyTemplate(KotlinHierarchyTemplate.default) {
        group("common") {
            group("jsHosted") {
                withJs()
                withWasm() // FIXME: KT-63417 – to be split into `withWasmJs` and `withWasmWasi`
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.kotlin.test)
//                implementation(libs.kotest.framework.engine)
//                implementation(libs.kotest.assertion)
//                implementation(libs.kotest.property)
            }
        }

//        val androidMain by getting
//        val desktopMain by getting
        val jsTest by getting {
            dependencies {
//                implementation(libs.kotest.framework.engine.js)
            }
        }

//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit5"))
//                implementation("io.kotest:kotest-runner-junit5:5.8.1")
//            }
//        }
    }
}

android {
    namespace = "com.github.sidky.graphik"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}