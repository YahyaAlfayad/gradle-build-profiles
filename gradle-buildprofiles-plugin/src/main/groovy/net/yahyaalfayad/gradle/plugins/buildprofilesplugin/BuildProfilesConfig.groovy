package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Project

class BuildProfilesConfig {

    final Project project
    // TODO default initialize from available plugins
    List programingLanguages
    //TODO choose better names
    List allProfiles = []
    List activeProfiles = []

    BuildProfilesConfig(Project project) {
        this.project = project
        this.programingLanguages = initializeProgramingLanguages()
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
}
