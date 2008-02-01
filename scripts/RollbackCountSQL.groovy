includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': '''Writes SQL to roll back the specified number of changes to STDOUT.
Example: grails rollback-count-sql 3
''') {
    depends(setup)

    try {
        liquibase.rollback(Integer.parseInt(args), null, new OutputStreamWriter(System.out));
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
