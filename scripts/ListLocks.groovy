import org.codehaus.groovy.grails.compiler.support.*
import java.io.OutputStreamWriter;
import java.util.*;
import java.text.*;

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File("scripts/LiquibaseSetup.groovy")

task ('default':''Lists who currently has locks on the database changelog''') {
    depends(setup)

    try {
        migrator.reportLocks(System.out)
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        migrator.getDatabase().getConnection().close();
    }
}
