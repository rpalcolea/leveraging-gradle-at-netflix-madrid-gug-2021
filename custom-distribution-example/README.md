# custom distribution example

`./gradlew publishNebulaPublicationToDistMavenRepository` will publish a custom distribution `all` and `bin` under `build/distributions`

The most important part is:

```
 def zipDist = task(zipTaskName, type: Zip, dependsOn: unzipTaskName) {
        description 'Add extra files to My Company Gradle distribution'
        archiveBaseName.set 'my-company-gradle-distribution'
        archiveClassifier.set type
        from unzip.destinationDir
        into(downloadGradle.distributionNameBase) {
            /**
             * Add init.d scripts
             * More info https://docs.gradle.org/current/userguide/init_scripts.html#sec:using_an_init_script
             */
            into('init.d') {
                from file('init.d')
            }
            /**
             * Custom gradle properties for a company
             * More details in https://docs.gradle.org/5.5/release-notes.html#define-organization-wide-properties-with-a-custom-gradle-distribution
             */
            from file('defaults/gradle.properties')
        }
    }
```

We are copying all from `init.d` which are init scripts. Our init script adds default dependencies to the classpath as example

In addition, we can add default gradle properties for projects, on this case we do `org.gradle.jvmargs=-Xmx2g`

If you run the `sample-project` with the generated wrapper you will see:

```
You are using a custom gradle distribution for your company

> Task :buildEnvironment

------------------------------------------------------------
Root project 'sample-project'
------------------------------------------------------------

classpath
+--- com.netflix.nebula:nebula-publishing-plugin:latest.release -> 17.3.2
|    +--- com.netflix.nebula:gradle-info-plugin:8.2.0
|    |    +--- com.perforce:p4java:2015.2.1365273
|    |    |    \--- com.jcraft:jzlib:1.1.2
|    |    +--- com.netflix.nebula:gradle-contacts-plugin:5.1.0
|    |    +--- org.eclipse.jgit:org.eclipse.jgit:5.7.0.202003110725-r
|    |    |    +--- com.jcraft:jsch:0.1.55
|    |    |    +--- com.jcraft:jzlib:1.1.1 -> 1.1.2
|    |    |    +--- com.googlecode.javaewah:JavaEWAH:1.1.7
|    |    |    +--- org.slf4j:slf4j-api:1.7.2
|    |    |    +--- org.bouncycastle:bcpg-jdk15on:1.64
|    |    |    |    \--- org.bouncycastle:bcprov-jdk15on:1.64
|    |    |    +--- org.bouncycastle:bcprov-jdk15on:1.64
|    |    |    \--- org.bouncycastle:bcpkix-jdk15on:1.64
|    |    |         \--- org.bouncycastle:bcprov-jdk15on:1.64
|    |    \--- org.tmatesoft.svnkit:svnkit:1.8.12
|    |         +--- com.jcraft:jsch.agentproxy.svnkit-trilead-ssh2:0.0.7
|    |         |    +--- com.trilead:trilead-ssh2:1.0.0-build217 -> 1.0.0-build220
|    |         |    \--- com.jcraft:jsch.agentproxy.core:0.0.7
|    |         +--- com.trilead:trilead-ssh2:1.0.0-build220
|    |         +--- net.java.dev.jna:jna-platform:4.1.0
|    |         |    \--- net.java.dev.jna:jna:4.1.0
|    |         +--- net.java.dev.jna:jna:4.1.0
|    |         +--- com.jcraft:jsch.agentproxy.connector-factory:0.0.7
|    |         |    +--- com.jcraft:jsch.agentproxy.core:0.0.7
|    |         |    +--- com.jcraft:jsch.agentproxy.usocket-jna:0.0.7
|    |         |    |    +--- com.jcraft:jsch.agentproxy.core:0.0.7
|    |         |    |    +--- net.java.dev.jna:jna:3.4.0 -> 4.1.0
|    |         |    |    \--- net.java.dev.jna:platform:3.4.0
|    |         |    +--- com.jcraft:jsch.agentproxy.usocket-nc:0.0.7
|    |         |    |    \--- com.jcraft:jsch.agentproxy.core:0.0.7
|    |         |    +--- com.jcraft:jsch.agentproxy.sshagent:0.0.7
|    |         |    |    \--- com.jcraft:jsch.agentproxy.core:0.0.7
|    |         |    \--- com.jcraft:jsch.agentproxy.pageant:0.0.7
|    |         |         +--- com.jcraft:jsch.agentproxy.core:0.0.7
|    |         |         +--- net.java.dev.jna:jna:3.4.0 -> 4.1.0
|    |         |         \--- net.java.dev.jna:platform:3.4.0
|    |         +--- de.regnis.q.sequence:sequence-library:1.0.3
|    |         \--- org.tmatesoft.sqljet:sqljet:1.1.10
|    |              \--- org.antlr:antlr-runtime:3.4
|    +--- com.netflix.nebula:gradle-contacts-plugin:5.1.0
|    +--- com.netflix.nebula:nebula-gradle-interop:1.0.11
|    |    \--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.50 -> 1.3.70
|    |         +--- org.jetbrains.kotlin:kotlin-stdlib:1.3.70
|    |         |    +--- org.jetbrains.kotlin:kotlin-stdlib-common:1.3.70
|    |         |    \--- org.jetbrains:annotations:13.0
|    |         \--- org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.70
|    |              \--- org.jetbrains.kotlin:kotlin-stdlib:1.3.70 (*)
|    \--- com.fasterxml.jackson:jackson-bom:2.9.9.20190807
\--- com.netflix.nebula:gradle-lint-plugin:latest.release -> 16.17.0
     +--- com.netflix.nebula:nebula-gradle-interop:1.0.11 (*)
     +--- org.apache.maven:maven-model-builder:3.6.3
     |    +--- org.codehaus.plexus:plexus-utils:3.2.1
     |    +--- org.codehaus.plexus:plexus-interpolation:1.25
     |    +--- javax.inject:javax.inject:1
     |    +--- org.apache.maven:maven-model:3.6.3
     |    |    \--- org.codehaus.plexus:plexus-utils:3.2.1
     |    +--- org.apache.maven:maven-artifact:3.6.3
     |    |    +--- org.codehaus.plexus:plexus-utils:3.2.1
     |    |    \--- org.apache.commons:commons-lang3:3.8.1
     |    +--- org.apache.maven:maven-builder-support:3.6.3
     |    \--- org.eclipse.sisu:org.eclipse.sisu.inject:0.3.4
     +--- com.google.guava:guava:19.0
     +--- org.codehaus.gpars:gpars:1.2.1
     |    +--- org.multiverse:multiverse-core:0.7.0
     |    \--- org.codehaus.jsr166-mirror:jsr166y:1.7.0
     \--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.70 (*)

(*) - dependencies omitted (listed previously)

A web-based, searchable dependency report is available by adding the --scan option.
```

Please refer to https://docs.gradle.org/current/userguide/init_scripts.html for more examples on init scripts.

Some ideas:

* repository configuration
* cache configuration
* provided plugins/dependencies
* Metadata (status) configuration