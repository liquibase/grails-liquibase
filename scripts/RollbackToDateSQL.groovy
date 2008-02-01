import java.text.DateFormat
import java.text.SimpleDateFormat

includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Writes SQL to roll back the database to that state it was in at the given date/time version to STDOUT.
Example: grails rollback-to-date-sql 2007-05-15 18:15:12
''') {
    depends(setup)

    try {
        def DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        liquibase.rollback(dateFormat.parse(liquibase.util.StringUtils.join(Arrays.asList(args), " ").replaceAll("\n", " ")), null, new PrintWriter(System.out));
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
