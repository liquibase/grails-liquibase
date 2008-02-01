includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Outputs list of unrun changesets''') {
    depends(setup)

    try {
        liquibase.reportStatus(true, System.out)
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
