includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Writes SQL to mark all changes as executed in the database to STDOUT''') {
    depends(setup)

    try {
        liquibase.changeLogSync(null, new PrintWriter(System.out))
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to update database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
