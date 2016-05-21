package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Project

class BuildProfilesConfig {

    final Project project
    final Utils utils;

    List programingLanguages

    List buildProfiles = []
    List activeBuildProfiles = []

    public BuildProfilesConfig(Project project) {
        this.project = project
        this.utils = new Utils(project)
        programingLanguages = initializeProgramingLanguages()
    }

    private final List initializeProgramingLanguages() {
        List programingLanguages = []
        final List supportedProgramingLanguages = ['java', 'groovy', 'scala']

        supportedProgramingLanguages.forEach { programingLanguage ->

            if (project.plugins.hasPlugin(programingLanguage)) {
                programingLanguages << programingLanguage
            }
        }
        return programingLanguages
    }

    void setActiveBuildProfiles(final List activeBuildProfiles) {
        this.activeBuildProfiles = activeBuildProfiles
        updateSourcesForProfiles()
    }

    List getActiveBuildProfiles() {
        return (activeBuildProfiles ?: buildProfiles)
    }

    void setBuildProfiles(final List buildProfiles) {
        this.buildProfiles = buildProfiles
        updateSourcesForProfiles()
    }

    private void updateSourcesForProfiles() {

        // remove build profiles before adding only active ones
        // needed in case that buildProfiles was set before active build profiles
        project.sourceSets.main.java.srcDirs.remove getBuildProfiles()

        getActiveBuildProfiles().forEach { buildProfile ->
            utils.updateAllFoldersForProfile(buildProfile, getProgramingLanguages())
        }
    }
}
