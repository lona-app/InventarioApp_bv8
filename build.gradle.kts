plugins {
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    // 4. GARANTA QUE O PLUGIN DO KSP ESTÁ DEFINIDO AQUI
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}