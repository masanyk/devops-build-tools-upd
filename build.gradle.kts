import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  	id("org.springframework.boot") version "2.7.14"
  	id("io.spring.dependency-management") version "1.1.0"
  	kotlin("jvm") version "1.8.21"
  	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.serialization") version "1.8.21"
}

group = "academy.softserve"
version = "0.0.1-SNAPSHOT"
java {
	sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "11"
	}
}


// Frontend build tasks for Vite + React + TypeScript
import org.springframework.boot.gradle.tasks.run.BootRun
import org.apache.tools.ant.taskdefs.condition.Os

val npmCommand = if (Os.isFamily(Os.FAMILY_WINDOWS)) "npm.cmd" else "npm"

tasks.register<Exec>("npmInstall") {
    workingDir = file("ui")
    commandLine(npmCommand, "i")
}

tasks.register<Exec>("compileUi") {
    dependsOn("npmInstall")
    workingDir = file("ui")
    commandLine(npmCommand, "run", "build")
}

tasks.register<Copy>("copyUi") {
    dependsOn("compileUi")
    from("ui/dist")
    into("src/main/resources/static")
}

tasks.withType<BootRun> {
    dependsOn("copyUi")
}