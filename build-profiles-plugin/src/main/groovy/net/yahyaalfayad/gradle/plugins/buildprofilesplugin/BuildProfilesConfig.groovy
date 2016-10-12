package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils.SourceSetGraphCreator
import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils.UpdateUtils
import org.gradle.api.Project
import org.slf4j.LoggerFactory

import static net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils.GeneralUtils.commaSeparatedSystemPropertyAsList

class BuildProfilesConfig {

    static final List SUPPORTED_PROGRAMING_LANGUAGES = ['java',
                                                        'groovy',
                                                        'scala',
                                                        'kotlin',
                                                        'assembler',
                                                        'c',
                                                        'cpp',
                                                        'objective-c',
                                                        'objective-cpp']

    private static final logger = LoggerFactory.getLogger(BuildProfilesConfig.class)

    final Project project
    final UpdateUtils updateUtils;

    List<String> programingLanguages

    Map<String, SingleBuildProfileConfig> profiles = [:]

    BuildProfilesConfig(Project project) {

        this.project = project
        this.updateUtils = new UpdateUtils(project)

        programingLanguages = definedSupportedLanguages()
    }

    List definedSupportedLanguages() {

        List definedProgramingLanguages = []

        SUPPORTED_PROGRAMING_LANGUAGES.forEach { programingLanguage ->

            if (project.plugins.hasPlugin(programingLanguage)) {
                definedProgramingLanguages << programingLanguage
            }
        }

        return definedProgramingLanguages
    }

    List<SingleBuildProfileConfig> getActiveBuildProfiles() {

        def commandLineDefinedActiveProfiles = commaSeparatedSystemPropertyAsList('activeBuildProfiles')
        List result = []
        if (commandLineDefinedActiveProfiles) {
            commandLineDefinedActiveProfiles.forEach {
                result.add(profiles[it])
            }
            return result
        } else {
            profiles.values().forEach {
                if (it.active) {
                    result.add(it)
                }
            }
        }

        return result
    }

    /**
     * configure individual profile
     * @param profileName name of the new build profile.
     * @param profileConfig custom configuration of the specified profile. null if no custom config is needed.
     */
    void buildProfile(@DelegatesTo(value = SingleBuildProfileConfig,
            strategy = Closure.DELEGATE_ONLY) Closure profileConfig) {

        if (profileConfig) {
            createAndAddProfile(profileConfig)
            updateSourcesForProfiles()
            updateRepositoriesForProfiles()
            updateDependenciesForProfiles()
        }
    }

    void createAndAddProfile(Closure profileConfig) {
        // http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html#section-delegatesto
        def buildProfileConfig = new SingleBuildProfileConfig(project)

        def delegatedClosure = profileConfig.rehydrate(buildProfileConfig, this, this)
        delegatedClosure.resolveStrategy = Closure.DELEGATE_ONLY
        delegatedClosure()

        profiles[buildProfileConfig.name] = buildProfileConfig

        logger.debug("adding profile: $buildProfileConfig")
    }

    void updateRepositoriesForProfiles() {
        getActiveBuildProfiles().forEach {
            if (it.repositories) {
                project.repositories it.repositories
            }
        }
    }

    void updateDependenciesForProfiles() {
        getActiveBuildProfiles().forEach {
            if (it.dependencies) {
                project.dependencies it.dependencies
            }
        }
    }

    private void updateSourcesForProfiles() {

        // remove build profiles before adding only active ones
        // needed in case that buildProfiles was set before active build profiles
        project.sourceSets.main.java.srcDirs.remove profiles.keySet()

        logger.debug("updating sources for profiles: ${getActiveBuildProfiles()}")

        getActiveBuildProfiles().forEach { buildProfile ->
            if (buildProfile.sourceSets) {
                SourceSetGraphCreator sourceSetGraphCreator = new SourceSetGraphCreator(null, { List callTrace,
                                                                                                String name = null,
                                                                                                def val = null ->
                    if (val) {
                        def callChain = project.sourceSets
                        callTrace.each {
                            String sourceSetName = it[0]
                            callChain = callChain."$sourceSetName"
                        }

                        if (callChain."$name" in Iterable) {
                            callChain."$name" += val
                        } else {
                            callChain."$name" = val
                        }
                    }
                })
                sourceSetGraphCreator.handleClosure buildProfile.sourceSets
            } else {
                updateUtils.updateAllFoldersForProfile(buildProfile, definedSupportedLanguages())
            }
        }

        logger.debug("sources after update: ${project.sourceSets}")
    }
}
