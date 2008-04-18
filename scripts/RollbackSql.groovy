includeTargets << new File("${liquibasePluginDir}/scripts/LiquibaseSetup.groovy")

task('default': '''Writes SQL to roll back the database to that state it was in at when the tag was applied to STDOUT.
Example: grails rollback-sql aTag
''') {
    depends(setup)

    try {
        def outStream = (args) ? new PrintStream(args) : System.out
        liquibase.rollback(args, null, new OutputStreamWriter(System.out));
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}