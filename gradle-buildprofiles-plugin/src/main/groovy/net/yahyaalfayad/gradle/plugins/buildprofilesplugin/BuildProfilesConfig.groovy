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

    void setActiveBuildProfiles(List activeBuildProfiles) {
        this.activeBuildProfiles = activeBuildProfiles
        updateSourcesForProfiles()
    }

    List getActiveBuildProfiles() {
        return (activeBuildProfiles ?: buildProfiles)
    }

    void setBuildProfiles(List buildProfiles) {
        this.buildProfiles = buildProfiles
        updateSourcesForProfiles()
    }

    private void updateSourcesForProfiles() {

        getBuildProfiles().forEach { buildProfile ->
            project.sourceSets.main.java.srcDirs.remove buildProfile
        }

        getActiveBuildProfiles().forEach { buildProfile ->
            utils.updateMainSourcesForProfile(buildProfile, getProgramingLanguages())
        }
    }
}
