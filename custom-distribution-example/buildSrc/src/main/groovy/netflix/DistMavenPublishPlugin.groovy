package netflix

import groovy.transform.CompileStatic
import nebula.plugin.publishing.maven.MavenPublishPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension

@CompileStatic
class DistMavenPublishPlugin implements Plugin<Project> {
    static final String MAVEN_REPO_NAME = 'distMaven'
    static final String MAVEN_REPO_NAME_TASK = 'publishDistMaven'
    static final String TASK_GROUP = 'publishing'

    @Override
    void apply(Project project) {
        project.plugins.withType(MavenPublishPlugin).configureEach {
            project.extensions.configure(PublishingExtension) { PublishingExtension publishing ->
                publishing.repositories { RepositoryHandler repositories ->
                    repositories.maven { MavenArtifactRepository m ->
                        m.name = MAVEN_REPO_NAME
                        m.url = project.file("${project.buildDir}/${MAVEN_REPO_NAME}").toURI().toURL()
                    }
                }
            }

            project.tasks.register(MAVEN_REPO_NAME_TASK) {
                it.group = TASK_GROUP
                it.description = "Publishes Maven publications to Maven repository <buildDir>/$MAVEN_REPO_NAME."
                it.dependsOn 'publishNebulaPublicationToDistMavenRepository'
            }
        }
    }
}
