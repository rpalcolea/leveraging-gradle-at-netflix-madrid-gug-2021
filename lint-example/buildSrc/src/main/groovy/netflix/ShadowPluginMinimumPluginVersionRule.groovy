package netflix

import com.netflix.nebula.lint.rule.GradleLintRule
import com.netflix.nebula.lint.rule.GradleModelAware
import com.netflix.nebula.lint.rule.GradlePlugin
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.gradle.util.VersionNumber

class ShadowPluginMinimumPluginVersionRule  extends GradleLintRule implements GradleModelAware {
    String description = "Upgrades shadow plugin"
    private final String pluginId = 'com.github.johnrengelman.shadow'
    private final String minimumPluginVersion = '6.1.0'
    private String lastVersion = null
    private MethodCallExpression versionCall = null
    protected Map<MethodCallExpression, String> violatingCallsToReplaceAndMessageToShow = new HashMap<>()

    @Override
    void visitGradlePlugin(MethodCallExpression call, String conf, GradlePlugin plugin) {
        if (conf == 'id') {
            if (plugin.id == pluginId) {
                if (VersionComparator.isOlderVersion(lastVersion, minimumPluginVersion)) {
                    violatingCallsToReplaceAndMessageToShow.put(versionCall, "id \'$pluginId\' version \'$minimumPluginVersion\'")
                }
            }
            versionCall = null
        } else if (conf == 'version') {
            versionCall = call
            lastVersion = plugin.id
        }
    }

    @Override
    protected void visitClassComplete(ClassNode node) {
        violatingCallsToReplaceAndMessageToShow.each { entry ->
            MethodCallExpression call = entry.getKey()
            String change = entry.getValue()

            addBuildLintViolation("shadow plugin should be at least ${minimumPluginVersion}", call)
                    .replaceWith(call, change)
        }
    }

    private class VersionComparator {
        static boolean isOlderVersion(String version, String minimumVersion) {
            if (version == minimumVersion) {
                return false
            }

            String transformedVersion = version.replaceAll('\\+', '0')
            return VersionNumber.parse(transformedVersion) < VersionNumber.parse(minimumVersion)
        }
    }
}
