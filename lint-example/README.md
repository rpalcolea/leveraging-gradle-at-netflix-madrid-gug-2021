# Lint example

The Gradle Lint plugin is a pluggable and configurable linter tool for identifying and reporting on patterns of misuse or deprecations in Gradle scripts and related files. It is inspired by the excellent ESLint tool for Javascript and by the formatting in NPM's eslint-friendly-formatter package.

If you run `./gradlew build`, you should see:

```
 Task :autoLintGradle

This project contains lint violations. A complete listing of the violations follows.
Because none were serious, the build's overall status was unaffected.

warning   shadow-plugin-minimum-version      shadow plugin should be at least 6.1.0
build.gradle:5
id "com.github.johnrengelman.shadow" version "6.0.0"

warning   jcenter-cleanup                    we prefer  maven central over jcenter
build.gradle:9
jcenter()


```

If you enable `criticalRules`, the build should fail:

```


This project contains lint violations. A complete listing of the violations follows.
Because some were serious, the overall build status has been changed to FAILED

error     shadow-plugin-minimum-version      shadow plugin should be at least 6.1.0
build.gradle:5
id "com.github.johnrengelman.shadow" version "6.0.0"

error     jcenter-cleanup                    we prefer  maven central over jcenter
build.gradle:9
jcenter()

✖ 2 problems (2 errors, 0 warnings)

To apply fixes automatically, run fixGradleLint, review, and commit the changes.

FAILURE: Build failed with an exception.

> This build contains 2 critical lint violations

```

Executing `./gradlew fixLintGradle` should have the following output:

```
> Task :fixLintGradle

This project contains lint violations. A complete listing of my attempt to fix them follows. Please review and commit the changes.

fixed          shadow-plugin-minimum-version      shadow plugin should be at least 6.1.0
build.gradle:5
id "com.github.johnrengelman.shadow" version "6.0.0"

fixed          jcenter-cleanup                    we prefer  maven central over jcenter
build.gradle:9
jcenter()

Corrected 2 lint problems

```

* `jcenter()` was replaced with `mavenCentral()` in your `build.gradle`
* `id 'com.github.johnrengelman.shadow' version '6.0.0'` was replaced with `id 'com.github.johnrengelman.shadow' version '6.1.0'`
