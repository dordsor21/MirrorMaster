plugins {
    java
    `maven-publish`
}

plugins.withId("java") {
    the<JavaPluginExtension>().toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
        vendor.set(JvmVendorSpec.ADOPTOPENJDK)
    }
}

repositories {
    mavenCentral()

    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        url = uri("https://ci.athion.net/plugin/repository/tools/")
    }

    maven {
        url = uri("https://mvn.intellectualsites.com/content/groups/public/")
    }

    maven {
        url = uri("https://mvn.intellectualsites.com/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        url = uri("https://maven.enginehub.org/repo/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
    compileOnly("com.plotsquared:PlotSquared-Bukkit:6.2.1") {
        isTransitive = false
    }
    compileOnly("com.plotsquared:PlotSquared-Core:6.2.1")
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
