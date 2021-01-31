# ospackaging example

`gradle-ospackage-plugin` provides Gradle-based assembly of system packages, typically for RedHat and Debian based distributions, using a canonical Gradle Copy Specs

The debian portion leverages JDeb Java library.

```

ospackage {
    packageName = 'mysuperapp'
    version = '1.0.0'
    release = '1'

    /**
     * Required packages
     */
    requires('someInternalPackage')
    requires('ffmpeg')

    /**
     * Adding pre and post install scripts
     */
    preInstall file('scripts/preInstall.sh')
    postInstall file('scripts/postInstall.sh')

    /**
     * We will copy into this folder
     */
    into '/etc/mysuperapp'

    from(jar.outputs.files)

    /**
     * Copy runtime classpath libraries into lib folder
     */
    from(configurations.runtimeClasspath) {
        into 'lib'
    }

    /**
     * Copy resources under conf folder
     */
    from('src/main/resources') {
        fileType CONFIG | NOREPLACE
        into 'conf'
    }
}
```

If you run `./gradlew buildDeb` you should see the following files under `build/distributions`:

```
-rw-r--r--  1 roberto x 576 Jan 31 01:09 mysuperapp_1.0.0-1_all.changes
-rw-r--r--  1 roberto x 15M Jan 31 01:09 mysuperapp_1.0.0-1_all.deb
```

You can extract the debian via `ar -x mysuperapp_1.0.0-1_all.deb`:

```
-rw-r--r--  1 roberto x 2.0K Jan 31 01:10 control.tar.gz
-rw-r--r--  1 roberto x  15M Jan 31 01:10 data.tar.gz
-rw-r--r--  1 roberto x    4 Jan 31 01:10 debian-binary
```

If you extract `control.tar.gz`, you should see our pre and post install scripts:

```
#!/bin/sh -e

case "$1" in
    install)

        set -ex

echo "pre-install script being executed"


        ;;
esac
#!/bin/sh -e

ec() {
    echo "$@" >&2
    "$@"
}

case "$1" in
    configure)



        set -ex

echo "post-install script being executed"


        ;;
esac
```

and the `control` file:

```
Package: mysuperapp
Source: mysuperapp
Version: 1.0.0-1
Section: java
Priority: optional
Architecture: all
Depends: someInternalPackage, ffmpeg
Installed-Size: 16461
Maintainer: roberto
Description: mysuperapp
```

Notice that we are asking for two packages which are marked as dependencies

In addition, `data.tar.gz` should contain the following: 

```
drwxr-xr-x  0 roberto 0           0 Jan 31 01:09 ./etc/
drwxr-xr-x  0 roberto 0           0 Jan 31 01:09 ./etc/mysuperapp/
-rw-r--r--  0 roberto 0        2121 Jan 31 01:09 ./etc/mysuperapp/ospackaging-example.jar
drwxr-xr-x  0 roberto 0           0 Jan 31 01:09 ./etc/mysuperapp/lib/
-rw-r--r--  0 roberto 0     2792264 Jan 31 01:09 ./etc/mysuperapp/lib/guava-29.0-jre.jar
-rw-r--r--  0 roberto 0       74030 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-ant-2.5.13.jar
-rw-r--r--  0 roberto 0       75486 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-cli-commons-2.5.13.jar
-rw-r--r--  0 roberto 0      453319 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-groovysh-2.5.13.jar
-rw-r--r--  0 roberto 0      751763 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-console-2.5.13.jar
-rw-r--r--  0 roberto 0      142979 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-groovydoc-2.5.13.jar
-rw-r--r--  0 roberto 0      129183 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-docgenerator-2.5.13.jar
-rw-r--r--  0 roberto 0       64585 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-cli-picocli-2.5.13.jar
-rw-r--r--  0 roberto 0       19118 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-datetime-2.5.13.jar
-rw-r--r--  0 roberto 0      134565 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-jmx-2.5.13.jar
-rw-r--r--  0 roberto 0      132798 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-json-2.5.13.jar
-rw-r--r--  0 roberto 0       21776 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-jsr223-2.5.13.jar
-rw-r--r--  0 roberto 0      238921 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-macro-2.5.13.jar
-rw-r--r--  0 roberto 0       19795 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-nio-2.5.13.jar
-rw-r--r--  0 roberto 0       26759 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-servlet-2.5.13.jar
-rw-r--r--  0 roberto 0       84087 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-sql-2.5.13.jar
-rw-r--r--  0 roberto 0      373255 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-swing-2.5.13.jar
-rw-r--r--  0 roberto 0      101188 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-templates-2.5.13.jar
-rw-r--r--  0 roberto 0       84878 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-test-2.5.13.jar
-rw-r--r--  0 roberto 0       13555 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-test-junit5-2.5.13.jar
-rw-r--r--  0 roberto 0        9767 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-testng-2.5.13.jar
-rw-r--r--  0 roberto 0      222697 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-xml-2.5.13.jar
-rw-r--r--  0 roberto 0     5630533 Jan 31 01:09 ./etc/mysuperapp/lib/groovy-2.5.13.jar
-rw-r--r--  0 roberto 0        4617 Jan 31 01:09 ./etc/mysuperapp/lib/failureaccess-1.0.1.jar
-rw-r--r--  0 roberto 0        2199 Jan 31 01:09 ./etc/mysuperapp/lib/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar
-rw-r--r--  0 roberto 0       19936 Jan 31 01:09 ./etc/mysuperapp/lib/jsr305-3.0.2.jar
-rw-r--r--  0 roberto 0      201116 Jan 31 01:09 ./etc/mysuperapp/lib/checker-qual-2.11.1.jar
-rw-r--r--  0 roberto 0       13879 Jan 31 01:09 ./etc/mysuperapp/lib/error_prone_annotations-2.3.4.jar
-rw-r--r--  0 roberto 0        8781 Jan 31 01:09 ./etc/mysuperapp/lib/j2objc-annotations-1.3.jar
-rw-r--r--  0 roberto 0      121573 Jan 31 01:09 ./etc/mysuperapp/lib/ant-junit-1.9.15.jar
-rw-r--r--  0 roberto 0     2070320 Jan 31 01:09 ./etc/mysuperapp/lib/ant-1.9.15.jar
-rw-r--r--  0 roberto 0       18425 Jan 31 01:09 ./etc/mysuperapp/lib/ant-launcher-1.9.15.jar
-rw-r--r--  0 roberto 0       11680 Jan 31 01:09 ./etc/mysuperapp/lib/ant-antlr-1.9.15.jar
-rw-r--r--  0 roberto 0       53820 Jan 31 01:09 ./etc/mysuperapp/lib/commons-cli-1.4.jar
-rw-r--r--  0 roberto 0      382233 Jan 31 01:09 ./etc/mysuperapp/lib/picocli-4.3.2.jar
-rw-r--r--  0 roberto 0      179818 Jan 31 01:09 ./etc/mysuperapp/lib/qdox-1.12.1.jar
-rw-r--r--  0 roberto 0      268780 Jan 31 01:09 ./etc/mysuperapp/lib/jline-2.14.6.jar
-rw-r--r--  0 roberto 0      314932 Jan 31 01:09 ./etc/mysuperapp/lib/junit-4.12.jar
-rw-r--r--  0 roberto 0      177798 Jan 31 01:09 ./etc/mysuperapp/lib/junit-jupiter-engine-5.4.2.jar
-rw-r--r--  0 roberto 0      134167 Jan 31 01:09 ./etc/mysuperapp/lib/junit-jupiter-api-5.4.2.jar
-rw-r--r--  0 roberto 0       97844 Jan 31 01:09 ./etc/mysuperapp/lib/junit-platform-launcher-1.4.2.jar
-rw-r--r--  0 roberto 0      822394 Jan 31 01:09 ./etc/mysuperapp/lib/testng-6.13.1.jar
-rw-r--r--  0 roberto 0       45024 Jan 31 01:09 ./etc/mysuperapp/lib/hamcrest-core-1.3.jar
-rw-r--r--  0 roberto 0      141848 Jan 31 01:09 ./etc/mysuperapp/lib/junit-platform-engine-1.4.2.jar
-rw-r--r--  0 roberto 0        7121 Jan 31 01:09 ./etc/mysuperapp/lib/opentest4j-1.1.1.jar
-rw-r--r--  0 roberto 0       69254 Jan 31 01:09 ./etc/mysuperapp/lib/jcommander-1.72.jar
-rw-r--r--  0 roberto 0       89363 Jan 31 01:09 ./etc/mysuperapp/lib/junit-platform-commons-1.4.2.jar
drwxr-xr-x  0 roberto 0           0 Jan 31 01:09 ./etc/mysuperapp/conf/
-rw-r--r--  0 roberto 0           9 Jan 31 01:09 ./etc/mysuperapp/conf/myconfig.conf
```

