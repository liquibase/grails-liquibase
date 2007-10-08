import org.codehaus.groovy.grails.compiler.support.*
import java.io.OutputStreamWriter;
import java.util.*;
import java.text.*;

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File("scripts/LiquibaseSetup.groovy")

task ('default':'''Writes Change Log XML to copy the current state of the database to standard out''') {
    depends(setup)

    try {
        def diff = classLoader.loadClass("liquibase.diff.Diff").getConstructor(java.sql.Connection.class).newInstance(connection);
//        diff.addStatusListener(new OutDiffStatusListener());
        def diffResult = diff.compare();

        diffResult.printChangeLog(System.out, migrator.getDatabase(), classLoader.loadClass("org.liquibase.grails.GrailsXmlWriter").getConstructor().newInstance());
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        migrator.getDatabase().getConnection().close();
    }
}
