plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.unipet7.mcommerce"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.unipet7.mcommerce"
        minSdk = 28
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //CircleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //Import CardView
    implementation ("androidx.cardview:cardview:1.0.0")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("jp.wasabeef:glide-transformations:4.3.0")
    //Round imageview
    implementation ("com.makeramen:roundedimageview:2.3.0")

    // splash screen library
    implementation(libs.core.splashscreen)
    // lottie animation library
    implementation(libs.lottie)
    // page indicator
    implementation(libs.dotsindicator)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}