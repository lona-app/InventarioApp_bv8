plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "br.com.inventarioapp_bv8"
    compileSdk = 36

    defaultConfig {
        applicationId = "br.com.inventarioapp_bv8"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "8.0.0" // Beta 8, Fase 0, Versão 0

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Core do Android e Kotlin
    implementation("androidx.core:core-ktx:1.13.1") // Extensões Kotlin para o Android Jetpack.
    implementation("androidx.appcompat:appcompat:1.6.1") // Suporte para componentes de UI em versões antigas.

    // UI - Componentes visuais
    implementation("com.google.android.material:material:1.12.0") // Componentes do Material Design.
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Para criar layouts flexíveis.

    // Testes
    testImplementation("junit:junit:4.13.2") // Framework de teste unitário.
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Extensões JUnit para testes instrumentados.
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Para escrever testes de UI.
}