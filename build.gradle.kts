plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.mockk:mockk:1.13.10")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.slf4j:slf4j-api:2.0.7")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runServer") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("server.MainKt")
    args = listOf("saved.json")
    environment("JAVA_TOOL_OPTIONS", "")
    jvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-Dstdin.encoding=UTF-8")
}

tasks.register<JavaExec>("runClient") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("client.MainKt")
    standardInput = System.`in`
    environment("JAVA_TOOL_OPTIONS", "")
    jvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-Dstdin.encoding=UTF-8")

}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "server.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}