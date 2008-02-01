includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': "Updates a database to the current version.") {
    depends(setup)

    try {
        System.out.println("Migrating ${grailsEnv} database");
        liquibase.update(null)
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
