includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Releases all locks on the database changelog''') {
    depends(setup)

    try {
        liquibase.forceReleaseLocks()
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
