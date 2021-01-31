# Release example

`nebula.info`: Non invasively collects information about the environment, and make information available to other plugins in a statically typed way. When possible lazily calculate info.

The module is made of three types of plugins.

* Collecting plugins, e.g. scm-info and ci-info. Both of those examples use specific implementations to detect the current environment and report values to the broker.
* Reporting plugins, e.g. Jar manifests, a generated file. These have specific formatting and outputting requirements, so they pull from the Broker. These will force values into
* A Broker plugin, to collect values from Collecting plugins and make them available to reporting plugins

`nebula.maven-publish`: Configures maven publication and adds metadata from environment

If you run `./gradlew publishNebulaPublicationToDistMavenRepository`, you should see `build/distMaven/netflix/nebula/release-example/1.0.0` folder which contains:

```
-rw-r--r--  1 roberto x 1.2K Jan 31 01:38 release-example-1.0.0-javadoc.jar
-rw-r--r--  1 roberto x   32 Jan 31 01:38 release-example-1.0.0-javadoc.jar.md5
-rw-r--r--  1 roberto x   40 Jan 31 01:38 release-example-1.0.0-javadoc.jar.sha1
-rw-r--r--  1 roberto x   64 Jan 31 01:38 release-example-1.0.0-javadoc.jar.sha256
-rw-r--r--  1 roberto x  128 Jan 31 01:38 release-example-1.0.0-javadoc.jar.sha512
-rw-r--r--  1 roberto x 1.7K Jan 31 01:38 release-example-1.0.0-sources.jar
-rw-r--r--  1 roberto x   32 Jan 31 01:38 release-example-1.0.0-sources.jar.md5
-rw-r--r--  1 roberto x   40 Jan 31 01:38 release-example-1.0.0-sources.jar.sha1
-rw-r--r--  1 roberto x   64 Jan 31 01:38 release-example-1.0.0-sources.jar.sha256
-rw-r--r--  1 roberto x  128 Jan 31 01:38 release-example-1.0.0-sources.jar.sha512
-rw-r--r--  1 roberto x 2.7K Jan 31 01:38 release-example-1.0.0.jar
-rw-r--r--  1 roberto x   32 Jan 31 01:38 release-example-1.0.0.jar.md5
-rw-r--r--  1 roberto x   40 Jan 31 01:38 release-example-1.0.0.jar.sha1
-rw-r--r--  1 roberto x   64 Jan 31 01:38 release-example-1.0.0.jar.sha256
-rw-r--r--  1 roberto x  128 Jan 31 01:38 release-example-1.0.0.jar.sha512
-rw-r--r--  1 roberto x 3.6K Jan 31 01:38 release-example-1.0.0.module
-rw-r--r--  1 roberto x   32 Jan 31 01:38 release-example-1.0.0.module.md5
-rw-r--r--  1 roberto x   40 Jan 31 01:38 release-example-1.0.0.module.sha1
-rw-r--r--  1 roberto x   64 Jan 31 01:38 release-example-1.0.0.module.sha256
-rw-r--r--  1 roberto x  128 Jan 31 01:38 release-example-1.0.0.module.sha512
-rw-r--r--  1 roberto x 3.1K Jan 31 01:38 release-example-1.0.0.pom
-rw-r--r--  1 roberto x   32 Jan 31 01:38 release-example-1.0.0.pom.md5
-rw-r--r--  1 roberto x   40 Jan 31 01:38 release-example-1.0.0.pom.sha1
-rw-r--r--  1 roberto x   64 Jan 31 01:38 release-example-1.0.0.pom.sha256
-rw-r--r--  1 roberto x  128 Jan 31 01:38 release-example-1.0.0.pom.sha512
```

If you inspect the POM, you will see metadata that was collected from the environment and also dynamic dependencies should be translated into resolved versions:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <!-- This module was also published with a richer model, Gradle metadata,  -->
  <!-- which should be used instead. Do not delete the following line which  -->
  <!-- is to indicate to Gradle or any Gradle module metadata file consumer  -->
  <!-- that they should prefer consuming it instead. -->
  <!-- do_not_remove: published-with-gradle-metadata -->
  <modelVersion>4.0.0</modelVersion>
  <groupId>netflix.nebula</groupId>
  <artifactId>release-example</artifactId>
  <version>1.0.0</version>
  <name>release-example</name>
  <url>https://github.com/rpalcolea/leveraging-gradle-at-netflix-madrid-gug-2021</url>
  <scm>
    <url>git@github.com:rpalcolea/leveraging-gradle-at-netflix-madrid-gug-2021.git</url>
  </scm>
  <dependencies>
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-math3</artifactId>
      <version>3.6.1</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.12.1</version>
      <scope>runtime</scope>
    </dependency>
    <dependency>
      <groupId>org.codehaus.groovy</groupId>
      <artifactId>groovy-all</artifactId>
      <version>2.5.13</version>
      <scope>runtime</scope>
    </dependency>
    <dependency>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
      <version>29.0-jre</version>
      <scope>runtime</scope>
    </dependency>
  </dependencies>
  <properties>
    <nebula_Manifest_Version>1.0</nebula_Manifest_Version>
    <nebula_Implementation_Title>netflix.nebula#release-example;1.0.0</nebula_Implementation_Title>
    <nebula_Implementation_Version>1.0.0</nebula_Implementation_Version>
    <nebula_Built_Status>integration</nebula_Built_Status>
    <nebula_Built_By>rperezalcolea</nebula_Built_By>
    <nebula_Built_OS>Mac OS X</nebula_Built_OS>
    <nebula_Build_Date>2021-01-31_01:38:32</nebula_Build_Date>
    <nebula_Gradle_Version>6.8.1</nebula_Gradle_Version>
    <nebula_Module_Source>/release-example</nebula_Module_Source>
    <nebula_Module_Origin>git@github.com:rpalcolea/leveraging-gradle-at-netflix-madrid-gug-2021.git</nebula_Module_Origin>
    <nebula_Change>c0f0ea3</nebula_Change>
    <nebula_Full_Change>c0f0ea39c7981ea0bc1ffd171fa16756a41a9a68</nebula_Full_Change>
    <nebula_Branch>main</nebula_Branch>
    <nebula_Build_Host>nfml-rperezalcolea1EM</nebula_Build_Host>
    <nebula_Build_Job>LOCAL</nebula_Build_Job>
    <nebula_Build_Number>LOCAL</nebula_Build_Number>
    <nebula_Build_Id>LOCAL</nebula_Build_Id>
    <nebula_Created_By>1.8.0_265-b11 (Azul Systems, Inc.)</nebula_Created_By>
    <nebula_Build_Java_Version>1.8.0_265</nebula_Build_Java_Version>
    <nebula_X_Compile_Target_JDK>1.8</nebula_X_Compile_Target_JDK>
    <nebula_X_Compile_Source_JDK>1.8</nebula_X_Compile_Source_JDK>
  </properties>
</project>
```

