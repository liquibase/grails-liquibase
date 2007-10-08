import org.codehaus.groovy.grails.compiler.support.*
import java.io.OutputStreamWriter;
import java.util.*;
import java.text.*;

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File("scripts/LiquibaseSetup.groovy")

task ('default':'''Writes SQL to roll back the database to that state it was in at the given date/time version to STDOUT.
Example: grails rollback-to-date-sql 2007-05-15 18:15:12
''') {
    depends(setup)

    try {
        def DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        migrator.rollbackToDate(dateFormat.parse(args), new OutputStreamWriter(System.out));
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        migrator.getDatabase().getConnection().close();
    }
}
