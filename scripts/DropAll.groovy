includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Drops all objects in database owned by the connected user.
Example: grails dropAll
''') {
    depends(setup)

    try {
        liquibase.dropAll();
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to drop database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
