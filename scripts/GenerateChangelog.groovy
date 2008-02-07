import liquibase.diff.Diff
import liquibase.database.DatabaseFactory

includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Writes Change Log XML to copy the current state of the database to standard out''') {
    depends(setup)

    try {
        def diff = new Diff(DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection), null);
        //        diff.addStatusListener(new OutDiffStatusListener());
        def diffResult = diff.compare();

        diffResult.printChangeLog(System.out, liquibase.getDatabase(), classLoader.loadClass("org.liquibase.grails.GrailsXmlWriter").getConstructor().newInstance());
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
