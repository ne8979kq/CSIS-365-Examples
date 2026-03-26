There are two important additions you must make to certain files when you use Room and coroutines. Both are located under the Gradle Scripts dropdown outside your whole project.

The first is to build.gradle.kts at the app level (should say :app next to it). Add the following to the bottom of the dependencies:

    // Room runtime library - required for all Room functionality
    implementation(libs.androidx.room.runtime)

    // KSP annotation processor - generates Room implementation code
    ksp(libs.androidx.room.compiler)

    // Kotlin Extensions and Coroutines support - enables suspend functions
    implementation(libs.androidx.room.ktx)

And at the top, under plugins, add this single line:

    id("com.google.devtools.ksp")

Next, we need to add something to build.gradle.kts (Project-level), the other file you probably saw earlier. Add the following line:

    id("com.google.devtools.ksp") version "2.3.6" apply false

This line is needed for ksp, and the version is important to get right! That should be all you need to do, then make sure to rebuild your project and you'll be able to use Room databases!
