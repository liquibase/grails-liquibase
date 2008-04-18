includeTargets << new File("${liquibasePluginDir}/scripts/LiquibaseSetup.groovy")

task('default': '''Removes all saved checksums from database log. Useful for MD5Sum Check Failed errors''') {
    depends(setup)

    try {
        liquibase.clearCheckSums()
    }
    catch (Exception e) {
        e.printStackTrace()
        event("StatusFinal", ["Failed to migrate database ${grailsEnv}"])
        exit(1)
    } finally {
        liquibase.getDatabase().getConnection().close();
    }
}
