plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //FireBase
    id("com.google.gms.google-services")

    id("kotlin-parcelize")

    // Safeargs
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "devandroid.george.apptask"
    compileSdk = 34

    defaultConfig {
        applicationId = "devandroid.george.apptask"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Navigation
    val nav_version = "2.7.1"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // FireBase
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    // Authentication
    implementation("com.google.firebase:firebase-auth-ktx")
    // DataBAse
    implementation("com.google.firebase:firebase-database-ktx")
}