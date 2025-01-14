plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "be.kuleuven.gt.app3"
    compileSdk = 34

    defaultConfig {
        applicationId = "be.kuleuven.gt.app3"
        minSdk = 24
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.android.material:material:1.1.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("org.greenrobot:eventbus:3.0.0")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.3")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation ("com.android.volley:volley:1.1.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}