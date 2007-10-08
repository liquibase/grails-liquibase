import org.codehaus.groovy.grails.compiler.support.*
import java.io.OutputStreamWriter;

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File("scripts/LiquibaseSetup.groovy")

task ('default':'''Writes SQL to roll back the specified number of changes to STDOUT.
Example: grails rollback-count-sql 3
''') {
    depends(setup)

    try {
        migrator.rollbackCountSQL(Integer.parseInt(args), new OutputStreamWriter(System.out));
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        migrator.getDatabase().getConnection().close();
    }
}
