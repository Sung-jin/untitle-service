buildscript {
    val kotlin = "1.6.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlin")
        classpath(kotlin("gradle-plugin", version = kotlin))
        classpath(kotlin("serialization", version = kotlin))
        classpath(kotlin("allopen", version = kotlin))
        classpath(kotlin("noarg", version = kotlin))
    }
}

group = 'com.example'
version = '1.0.0'
sourceCompatibility = '11'

plugins {
    kotlin("kapt")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

//repositories {
//    mavenCentral()
//}

dependencies {
    implementation('org.springframework.boot:spring-boot-starter-web')
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation('org.springframework.boot:spring-boot-starter-security')
    implementation('org.springframework.boot:spring-boot-starter-data-jpa')
    implementation('net.logstash.logback:logstash-logback-encoder:6.6')
    implementation('mysql:mysql-connector-java')
    implementation('ch.qos.logback:logback-classic')
    implementation('ch.qos.logback:logback-core')
    implementation('org.apache.logging.log4j:log4j-core:2.17.0')
    implementation("org.springframework.session:spring-session-data-redis")

    annotationProcessor('org.projectlombok:lombok')

    compileOnly('org.projectlombok:lombok')

    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude module: 'junit'
    }
    testImplementation('org.springframework.security:spring-security-test')
    testImplementation('org.junit.jupiter:junit-jupiter:5.4.0')
    testImplementation('org.junit.jupiter:junit-jupiter-api:5.4.0')
    testImplementation('org.junit.jupiter:junit-jupiter-engine:5.4.0')
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
}

test {
    useJUnitPlatform()
}

jar {
    enabled = false
}

//compileKotlin {
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//}
//compileTestKotlin {
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//}


allOpen {
    annotation("javax.persistence.Entity")
}