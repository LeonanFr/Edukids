plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    // Usando o alias correto definido no libs.versions.toml
    alias(libs.plugins.jetbrainsKotlinCompose)
    id("com.google.gms.google-services")
}

android {
    namespace = "Edukids"
    // Corrigido: A versão máxima atual é 34.
    compileSdk = 36

    defaultConfig {
        applicationId = "com.Edukids.app"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/native-image/org.mongodb/bson/native-image.properties"
        }

    }
}

dependencies {

    // O BoM (Bill of Materials) deve vir primeiro. Ele gerencia as versões abaixo.
    implementation(platform(libs.firebase.bom))

// Dependências Firebase sem versão (BOM gerencia)
    implementation(libs.google.firebase.auth)
    implementation(libs.com.google.firebase.firebase.analytics)
    implementation(libs.google.firebase.database)


    implementation (libs.play.services.auth)
    implementation (libs.android.gif.drawable)
    implementation (libs.core)
    implementation (libs.androidx.media3.session)
    implementation (libs.glide)
    implementation (libs.gson)
    implementation (libs.okhttp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

