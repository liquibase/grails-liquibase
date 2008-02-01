includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Generates Javadoc-like documentation based on current database and change log''') {
    depends(setup)

    try {
        liquibase.generateDocumentation("dbdoc")
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
