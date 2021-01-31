package netflix

import com.netflix.nebula.lint.rule.GradleLintRule
import com.netflix.nebula.lint.rule.GradleModelAware
import org.codehaus.groovy.ast.expr.MethodCallExpression

class JcenterCleanupRule extends GradleLintRule implements GradleModelAware {
    String description = 'remove jcenter since we prefer maven central'

    @Override
    void visitMethodCallExpression(MethodCallExpression call) {
        if(call.methodAsString == 'jcenter') {
            addBuildLintViolation("we prefer  maven central over jcenter", call)
                    .replaceWith(call, 'mavenCentral()')
        }
    }
}
