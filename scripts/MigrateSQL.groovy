includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': "Writes SQL to update database to current version to STDOUT.") {
    depends(setup)

    try {
        liquibase.update(null, new PrintWriter(System.out));
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
