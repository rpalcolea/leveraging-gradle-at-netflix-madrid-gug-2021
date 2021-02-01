package nebula.dependencycache.builder

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

import static org.gradle.api.specs.Specs.SATISFIES_ALL

/**
 * This plugin will take the BOM that is declared in the sub project, take the dependencies from the POM and resolve them.
 * Current approach is to create a configuration for each dependency so we can get the cache for each one isolated without dependency conflict resolution
 */
class BomAnalyzerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            BomAnalyzer bomAnalyzer = new BomAnalyzer()
            project.configurations.nebulaRecommenderBom.copy().resolvedConfiguration.getFiles(SATISFIES_ALL).each {file ->
                if(!file.name.endsWith('.pom')) {
                    return
                }
                bomAnalyzer.analyze(file, project)
            }
        }
    }

    private class BomAnalyzer {

        String configurationName = 'nebulaDependencyBomAnalyzer'

        void analyze(File bomFile, Project project) {
            final pomXml = new XmlSlurper().parseText(bomFile.text)
            Configuration configuration = project.configurations.maybeCreate(configurationName)
            pomXml.dependencyManagement.dependencies.dependency.eachWithIndex { d, index ->
                def dependency = project.dependencies.create("${d.groupId.text()}:${d.artifactId.text()}")
                configuration.dependencies.add(dependency)
                project.configurations.compileClasspath.dependencies.add(dependency)
            }
        }
    }
}
