plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
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
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}
tasks.register("customSigningReport") {
    doLast {
        println("Signing report:")
        android.signingConfigs.forEach { config ->
            println("${config.name}:")
            println("Store: ${config.storeFile}")
            println("Alias: ${config.keyAlias}")

            // Execute keytool command to get SHA-1 fingerprint
            val sha1 =
                config.storeFile?.let {
                    config.keyAlias?.let { it1 ->
                        config.storePassword?.let { it2 ->
                            config.keyPassword?.let { it3 ->
                                getSha1(
                                    it,
                                    it1,
                                    it2,
                                    it3,
                                )
                            }
                        }
                    }
                }
            println("SHA-1: $sha1")
        }
    }
}

fun getSha1(
    keystore: File,
    alias: String,
    storePassword: String,
    keyPassword: String,
): String {
    val command = "keytool -list -v -keystore $keystore -storepass $storePassword -alias $alias -keypass $keyPassword"
    val process = Runtime.getRuntime().exec(command)
    process.waitFor()

    if (process.exitValue() == 0) {
        val output = process.inputStream.bufferedReader().readText()
        val regex = Regex("SHA1: ([\\w:]+)")
        val matchResult = regex.find(output)
        if (matchResult != null) {
            return matchResult.groupValues[1]
        }
    }

    return "Unable to get SHA-1"
}

dependencies {
    // CircleImageView
    implementation(libs.circleimageview)
    // Import CardView
    implementation(libs.cardview)
    // Glide
    implementation(libs.glide)
    implementation(libs.firebase.firestore)
    annotationProcessor(libs.compiler)
    implementation(libs.glide.transformations)
    // Round imageview
    implementation(libs.roundedimageview)

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
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.realtime.database)
    implementation(libs.firebase.storage)
    implementation(libs.gson)
}
