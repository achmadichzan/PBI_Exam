import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.variant.BuildConfigField

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

androidComponents {
    onVariants {
        it.buildConfigFields.put(
            "BASE_URL", BuildConfigField(
                "String", "\"https://pbiexam.000webhostapp.com/\"", "base url"
            )
        )
    }
}

android {
    namespace = "com.sipalingandroid.pbiexam"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sipalingandroid.pbiexam"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

create("benchmark") {
    initWith(buildTypes.getByName("release"))
    signingConfig = signingConfigs.getByName("debug")
    matchingFallbacks += listOf("release")
    isDebuggable = false
}
        //        debug {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            isCrunchPngs = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//        val debug:ApplicationBuildType by getting {
//            applicationIdSuffix = ".debug"
//        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
//            signingConfig = signingConfigs.getByName("debug")
        }

//        val benchmark: ApplicationBuildType by creating {
//            initWith(release)
//            signingConfig = signingConfigs.getByName("debug")
//            matchingFallbacks += listOf("release")
//            isDebuggable = false
//            proguardFiles("baseline-profiles-rules.pro")
//        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation:1.5.3")

    implementation("androidx.core:core-splashscreen:1.1.0-alpha02")

    // Material 3
    implementation("androidx.compose.material3:material3:1.1.2")

    implementation("androidx.compose.material:material:1.5.3")
    implementation("androidx.compose.material:material-icons-extended:1.5.3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Unit/UI Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Profiles
    implementation("androidx.profileinstaller:profileinstaller:1.3.1")

    // Compose ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // Accompanist WebView
    implementation("com.google.accompanist:accompanist-webview:0.32.0")

//    implementation("com.google.accompanist:accompanist-swiperefresh:0.32.0")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    // Image Loader
//    implementation("com.github.bumptech.glide:glide:4.16.0")
//    ksp("com.github.bumptech.glide:ksp:4.16.0")
//    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.5")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.coil-kt:coil-gif:2.4.0")

    // Animated NavBar
//    implementation("com.exyte:animated-navigation-bar:1.0.0")

    // Google fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.5.3")
}