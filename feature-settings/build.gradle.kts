/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.compose.compiler.report)
    id("com.mikepenz.aboutlibraries.plugin")
}

android {
    namespace = "com.ddd.pollpoll.feature.settings"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "com.ddd.pollpoll.core.testing.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    aboutLibraries {
        // - If the automatic registered android tasks are disabled, a similar thing can be achieved manually
        // - `./gradlew app:exportLibraryDefinitions -PexportPath=src/main/res/raw`
        // - the resulting file can for example be added as part of the SCM
        registerAndroidTasks = true
        // Define the output file name. Modifying this will disable the automatic meta data discovery for supported platforms.
        outputFileName = "aboutlibraries.json"
        // Allow to enable "offline mode", will disable any network check of the plugin (including [fetchRemoteLicense] or pulling spdx license texts)
        offlineMode = false
        // Enable fetching of "remote" licenses.  Uses the API of supported source hosts
        // See https://github.com/mikepenz/AboutLibraries#special-repository-support
        fetchRemoteLicense = true
        // Allows to exclude some fields from the generated meta data field.
        excludeFields = arrayOf("developers", "funding")
        // Define the strict mode, will fail if the project uses licenses not allowed
        // - This will only automatically fail for Android projects which have `registerAndroidTasks` enabled
        // For non Android projects, execute `exportLibraryDefinitions`
        strictMode = com.mikepenz.aboutlibraries.plugin.StrictMode.FAIL
        // Allowed set of licenses, this project will be able to use without build failure
        allowedLicenses = arrayOf("Apache-2.0", "asdkl", "mit", "mpl_2_0", "Play Core Software Development Kit Terms of Service")
        // Enable the duplication mode, allows to merge, or link dependencies which relate
        duplicationMode = com.mikepenz.aboutlibraries.plugin.DuplicateMode.MERGE
        // Configure the duplication rule, to match "duplicates" with
        duplicationRule = com.mikepenz.aboutlibraries.plugin.DuplicateRule.SIMPLE
    }

}

dependencies {
    implementation(project(":core-data"))
    implementation(project(":core-ui"))
    implementation(project(":core-designsystem"))
    androidTestImplementation(project(":core-testing"))
    implementation(project(":core-common"))

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Core Android dependencies
    implementation(libs.androidx.activity.compose)

    // Arch Components
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // Tooling
    debugImplementation(libs.androidx.compose.ui.tooling)
    // Instrumented tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Hilt and instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    // Hilt and Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)

    // Local tests: jUnit, coroutines, Android runner
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    // Instrumented tests: jUnit rules and runners
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
    implementation(libs.aboutlibraries.compose)
    implementation(libs.aboutlibraries.core)

}
