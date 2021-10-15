plugins {
    java
    `maven-publish`
}

plugins.withId("java") {
    the<JavaPluginExtension>().toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
        vendor.set(JvmVendorSpec.ADOPTOPENJDK)
    }
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://ci.athion.net/plugin/repository/tools/")
    }

    maven {
        url = uri("https://mvn.intellectualsites.com/content/groups/public/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    compileOnly("org.spigotmcv1_17_r1_2:spigotmcv1_17_r1_2:1_17_r1_2")
    compileOnly("com.plotsquared:PlotSquared-Bukkit:6.1.2") {
        isTransitive = false
    }
    compileOnly("com.plotsquared:PlotSquared-Core:6.1.2")
}

group = "be.mc.woutwoot"
version = "1"
description = "MirrorMaster"

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
