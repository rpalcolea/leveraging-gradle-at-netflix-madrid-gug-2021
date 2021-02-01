package nebula.dependencycache.builder

import org.gradle.api.internal.artifacts.ivyservice.CacheLayout
import org.gradle.util.GradleVersion

class CacheLayoutMapper {
    static getCacheLayout(String version) {
        GradleVersion gradleVersion = GradleVersion.version(version)
        String modulesVersion = CacheLayout.ROOT.versionMapping.getVersionUsedBy(gradleVersion).get().toString()
        String metadataVersion = CacheLayout.META_DATA.versionMapping.getVersionUsedBy(gradleVersion).get().toString()
        String fileStoreVersion = CacheLayout.FILE_STORE.versionMapping.getVersionUsedBy(gradleVersion).get().toString()
        return new CacheLayoutMapping(modulesVersion, fileStoreVersion, metadataVersion)
    }
}
