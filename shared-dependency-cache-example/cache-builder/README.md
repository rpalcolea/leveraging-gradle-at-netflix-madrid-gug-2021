# shared dependency cache

The dependency cache, both the file and metadata parts, are fully encoded using relative paths. This means that it is perfectly possible to copy a cache around and see Gradle benefit from it.

The path that can be copied is $GRADLE_HOME/caches/modules-<version>. The only constraint is placing it using the same structure at the destination, where the value of GRADLE_HOME can be different.

To download all dependencies

`./gradlew compileJava -g ./generated-shared-dependency-cache`

To zip the cache

`./gradlew zipCache -g ./generated-shared-dependency-cache`

We use a custom Gradle HOME, so we only capture the dependencies we care and not everything in our environment

Then set the `GRADLE_RO_DEP_CACHE` environment variable to point to the directory containing the cache:

```
$GRADLE_RO_DEP_CACHE
|-- modules-2 : the read-only dependency cache, should be mounted with read-only privileges

$GRADLE_HOME
|-- caches
|-- modules-2 : the container specific dependency cache, should be writable
|-- ...
|-- ...
```

If you run the sample project `./gradlew compileJava -g /tmp/sometempfolder -d > output.log`, you should see this in the output:

```
2021-02-01T13:24:46.400-0600 [DEBUG] [org.gradle.internal.operations.DefaultBuildOperationRunner] Build operation 'Create incremental compile snapshot for /Users/rperezalcolea/Projects/github/rpalcolea/leveraging-gradle-at-netflix-madrid-gug-2021/shared-dependency-cache-example/cache-builder/build/shared-dependency-cache/modules-2/files-2.1/com.amazonaws/aws-java-sdk-s3/1.11.946/f02cfc3abe7bd940a814ffe3bf93a333c71be2b3/aws-java-sdk-s3-1.11.946.jar' started
```

and

```
2021-02-01T13:24:45.562-0600 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleComponentRepository] Found artifact 'aws-java-sdk-core-1.11.946.jar (com.amazonaws:aws-java-sdk-core:1.11.946)' in resolver cache: /Users/rperezalcolea/Projects/github/rpalcolea/leveraging-gradle-at-netflix-madrid-gug-2021/shared-dependency-cache-example/cache-builder/build/shared-dependency-cache/modules-2/files-2.1/com.amazonaws/aws-java-sdk-core/1.11.946/4ab02b7ce31aef3804033e5112f2d2d86df1ac5e/aws-java-sdk-core-1.11.946.jar
```