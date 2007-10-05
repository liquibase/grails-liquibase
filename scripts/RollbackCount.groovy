import org.codehaus.groovy.grails.compiler.support.*
import java.io.OutputStreamWriter;

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File("scripts/LiquibaseSetup.groovy")

task ('default':'''Rolls back the specified number of changes.
Example: grails rollbackCount 3
''') {
    depends(setup)

    try {
        migrator.rollbackCount(Integer.parseInt(args));
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        migrator.getDatabase().getConnection().close();
    }
}
