object Android {
    const val compileSdk = 34
    const val minSdk = 26
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val coreKtx = "1.12.0"
    const val appcompat = "1.6.1"
    const val constraintlayout = "2.1.4"
    const val lifecycleViewmodel = "2.7.0"
    const val activityKtx = "1.8.2"
    const val fragmentKtx = "1.6.2"
    const val material = "1.11.0"

    const val room = "2.6.1"
    const val eventbus = "3.3.1"
    const val immersionbar = "3.2.2"


    const val junit = "4.13.2"
    const val junitExt = "1.1.5"
    const val espressoCore = "3.5.1"
}

object Libs {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodel}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val material = "com.google.android.material:material:${Versions.material}"


    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val eventbus = "org.greenrobot:eventbus:${Versions.eventbus}"

    const val immersionbar = "com.geyifeng.immersionbar:immersionbar:${Versions.immersionbar}"

    const val junit = "junit:junit:${Versions.junit}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}