import org.codehaus.groovy.grails.compiler.support.*

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File("scripts/LiquibaseSetup.groovy")

task ('default':"Updates a database to the current version.") {
    depends(setup)

    try {
        System.out.println("Migrating ${grailsEnv} database");
        migrator.migrate()
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        migrator.getDatabase().getConnection().close();
    }
}
