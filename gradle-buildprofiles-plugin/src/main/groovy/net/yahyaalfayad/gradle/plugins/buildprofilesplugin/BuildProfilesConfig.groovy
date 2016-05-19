package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Project

class BuildProfilesConfig {

    final Project project

    List programingLanguages

    List buildProfiles = []
    List activeBuildProfiles = []

    public BuildProfilesConfig(Project project) {
        this.project = project
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

    List getActiveBuildProfiles() {
        return (activeBuildProfiles ?: buildProfiles)
    }
}
