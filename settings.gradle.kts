pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenCentral() // For ImagePicker library
        maven {
            url= uri("https://jitpack.io")
        }  // For uCrop library

    }
}



rootProject.name = "MemoryPalaceApp"
include(":app")
