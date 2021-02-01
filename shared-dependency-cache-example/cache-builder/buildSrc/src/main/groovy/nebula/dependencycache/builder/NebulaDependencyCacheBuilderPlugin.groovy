package nebula.dependencycache.builder

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Zip

class NebulaDependencyCacheBuilderPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.rootProject != project) {
            return
        }

        CacheLayoutMapping cacheLayoutMapping = CacheLayoutMapper.getCacheLayout(project.gradle.gradleVersion)
        Provider<Copy> copyJarFilesTask = project.tasks.register('copyJarFiles', Copy)
        copyJarFilesTask.configure {
            from "${project.gradle.gradleUserHomeDir}/caches/modules-${cacheLayoutMapping.modulesVersion}/files-${cacheLayoutMapping.filesVersion}"
            into "${project.buildDir}/shared-dependency-cache/modules-${cacheLayoutMapping.modulesVersion}/files-${cacheLayoutMapping.filesVersion}"
        }

        Provider<Copy> copyMetadataFilesTask = project.tasks.register('copyMetadataFiles', Copy)
        copyMetadataFilesTask.configure {
            from "${project.gradle.gradleUserHomeDir}/caches/modules-${cacheLayoutMapping.modulesVersion}/metadata-${cacheLayoutMapping.metadataVersion}"
            into "${project.buildDir}/shared-dependency-cache/modules-${cacheLayoutMapping.modulesVersion}/metadata-${cacheLayoutMapping.metadataVersion}"
        }

        Provider<Zip> compressCacheTask = project.tasks.register('zipCache', Zip)
        compressCacheTask.configure {
            dependsOn(copyJarFilesTask, copyMetadataFilesTask)
            archiveFileName.set "nebula-shared-dependency-cache-${project.version}.zip"
            destinationDirectory.set project.buildDir
            from("${project.gradle.gradleUserHomeDir}/caches/modules-${cacheLayoutMapping.modulesVersion}/metadata-${cacheLayoutMapping.metadataVersion}") {
                into "/shared-dependency-cache/modules-${cacheLayoutMapping.modulesVersion}/metadata-${cacheLayoutMapping.metadataVersion}"
            }
            from("${project.gradle.gradleUserHomeDir}/caches/modules-${cacheLayoutMapping.modulesVersion}/files-${cacheLayoutMapping.filesVersion}") {
                into "/shared-dependency-cache/modules-${cacheLayoutMapping.modulesVersion}/files-${cacheLayoutMapping.filesVersion}"
            }
        }

    }
}
