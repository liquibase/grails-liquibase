includeTargets << new File("scripts/LiquibaseSetup.groovy")

task('default': "Applies the specified number of changes to a database.") {
    depends(setup)

    try {
        System.out.println("Migrating ${grailsEnv} database");
        liquibase.update(Integer.parseInt(args), null);
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().close();
    }
}
