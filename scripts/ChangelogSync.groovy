includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Mark all changes as executed in the database''') {
    depends(setup)

    try {
        liquibase.changeLogSync(null)
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to update database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
