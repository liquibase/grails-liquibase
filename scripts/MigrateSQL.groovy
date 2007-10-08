import org.codehaus.groovy.grails.compiler.support.*
import java.io.OutputStreamWriter;

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File("scripts/LiquibaseSetup.groovy")

task ('default':"Writes SQL to update database to current version to STDOUT.") {
    depends(setup)

    try {
        migrator.migrateSQL()
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        migrator.getDatabase().getConnection().close();
    }
}
