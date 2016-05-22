package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils.UpdateUtils
import org.gradle.api.Project

class BuildProfilesConfig {

    static final List SUPPORTED_PROGRAMING_LANGUAGES = ['java', 'groovy', 'scala']

    final Project project
    final UpdateUtils updateUtils;

    List programingLanguages

    List buildProfiles
    List activeBuildProfiles

    public BuildProfilesConfig(Project project) {

        this.project = project
        this.updateUtils = new UpdateUtils(project)

        programingLanguages = definedSupportedLanguages()
    }

    private final List definedSupportedLanguages() {

        List definedProgramingLanguages = []
        final List supportedProgramingLanguages = SUPPORTED_PROGRAMING_LANGUAGES

        supportedProgramingLanguages.forEach { programingLanguage ->

            if (project.plugins.hasPlugin(programingLanguage)) {
                definedProgramingLanguages << programingLanguage
            }
        }

        return definedProgramingLanguages
    }

    void setActiveBuildProfiles(final List activeBuildProfiles) {

        this.activeBuildProfiles = activeBuildProfiles
        updateSourcesForProfiles()
    }

    List getActiveBuildProfiles() {

        return (commaSeparatedSystemPropertyAsList('activeBuildProfiles') ?: (activeBuildProfiles ?: getBuildProfiles()))
    }

    private List commaSeparatedSystemPropertyAsList(String systemProperty) {

        return System.properties[systemProperty]?.split(',')?.collect {
            it.trim()
        }?.toList()
    }

    void setBuildProfiles(final List buildProfiles) {

        this.buildProfiles = buildProfiles
        updateSourcesForProfiles()
    }

    List getBuildProfiles() {
        return (commaSeparatedSystemPropertyAsList('buildProfiles') ?: (buildProfiles ?: []))
    }

    private void updateSourcesForProfiles() {

        // remove build profiles before adding only active ones
        // needed in case that buildProfiles was set before active build profiles
        project.sourceSets.main.java.srcDirs.remove getBuildProfiles()

        getActiveBuildProfiles().forEach { buildProfile ->
            updateUtils.updateAllFoldersForProfile(buildProfile, getProgramingLanguages())
        }
    }
}
