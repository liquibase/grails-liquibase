import org.codehaus.groovy.grails.compiler.support.*
import java.io.OutputStreamWriter;
import java.util.*;
import java.text.*;

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File("scripts/LiquibaseSetup.groovy")

task ('default':'''Removes all saved checksums from database log. Useful for MD5Sum Check Failed errors''') {
    depends(setup)

    try {
        migrator.clearCheckSums()
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        migrator.getDatabase().getConnection().close();
    }
}