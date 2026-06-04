plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(project(":common"))

    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("client.MainKt")
}

tasks.register<JavaExec>("runClient") {
    group = "application"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("client.MainKt")
    standardInput = System.`in`
    environment("JAVA_TOOL_OPTIONS", "")
    jvmArgs = listOf(
        "-Dfile.encoding=UTF-8",
        "-Dstdout.encoding=UTF-8",
        "-Dstderr.encoding=UTF-8",
        "-Dstdin.encoding=UTF-8"
    )
}