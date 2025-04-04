plugins {
    kotlin("kapt") // اعمال پلاگین kapt
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.behnamuix.karsaz"
    compileSdk = 35
    buildFeatures {
        viewBinding = true

    }
    defaultConfig {

        renderscriptTargetApi= 21 // یا بالاتر
        renderscriptSupportModeEnabled=true
    }



    defaultConfig {
        applicationId = "com.behnamuix.karsaz"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.gms.play.services.auth.api.phone)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.circularimageview)
    implementation(libs.glide)
    implementation(libs.persian.date.picker.dialog)
    implementation(libs.animate)
    implementation(libs.lottie)
    implementation(libs.volley)
    implementation(libs.otp.view)
    implementation (libs.switch.button)
    implementation(libs.library)
    implementation (libs.persian.date.picker.dialog)
    implementation(libs.androidx.viewpager2)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-rxjava3:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    //RXJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")




}