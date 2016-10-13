package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils

import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.SingleBuildProfileConfig
import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UpdateUtils {

    private static final Logger logger = LoggerFactory.getLogger(CreateUtils.class)

    private Project project

    UpdateUtils(Project project) {
        this.project = project
    }

    void updateAllFoldersForProfile(final def activeBuildProfile,
                                    final List currentlySpecifiedLanguages) {

        updateAllSourcesForProfile(activeBuildProfile, currentlySpecifiedLanguages)
        updateAllResourcesForProfile(activeBuildProfile)
    }

    void updateAllSourcesForProfile(final def activeBuildProfile,
                                    final List currentlySpecifiedLanguages) {

        updateMainSourcesForProfile(activeBuildProfile, currentlySpecifiedLanguages)
        updateTestSourcesForProfile(activeBuildProfile, currentlySpecifiedLanguages)
    }

    void updateMainSourcesForProfile(final def activeBuildProfile,
                                     final List currentlySpecifiedLanguages) {

        updateSourceSetBySourceSetAndProfile('main', activeBuildProfile, currentlySpecifiedLanguages)
    }

    void updateTestSourcesForProfile(final SingleBuildProfileConfig activeBuildProfile,
                                     final List currentlySpecifiedLanguages) {

        updateSourceSetBySourceSetAndProfile('test', activeBuildProfile, currentlySpecifiedLanguages)
    }

    void updateSourceSetBySourceSetAndProfile(final String sourceSet,
                                              final def buildProfile,
                                              final List currentlySpecifiedLanguages) {

        logger.debug("updating ${sourceSet} sources for profile: ${buildProfile}")

        currentlySpecifiedLanguages.each { specifiedProgramingLanguage ->

            logger.with {
                debug("updating ${sourceSet} sources for profile: ${buildProfile} " +
                        "and progrming language: ${specifiedProgramingLanguage}")
                debug("content of ${sourceSet} sources before update is: " +
                        "${project.sourceSets."$sourceSet"}")
                debug("adding the following source folder: " +
                        "src/${sourceSet}/${specifiedProgramingLanguage}-${buildProfile}")
            }

            project.sourceSets."${sourceSet}"."${specifiedProgramingLanguage}".srcDir "src/${sourceSet}/${specifiedProgramingLanguage}-${buildProfile}"

            logger.debug("content of ${sourceSet} sources after update is: ${project.sourceSets."$sourceSet"}")
        }
    }

    void updateAllResourcesForProfile(final def buildProfile) {
        updateResourcesByProfile('main', buildProfile)
        updateResourcesByProfile('test', buildProfile)
    }

    void updateResourcesByProfile(final String sourceSet,
                                  final def buildProfile) {

        logger.debug("updating ${sourceSet} resources for profile: ${buildProfile}")

        project.sourceSets."${sourceSet}".resources.srcDir "src/${sourceSet}/resources-${buildProfile}"

        logger.debug("content of ${sourceSet} resources after update is: ${project.sourceSets."$sourceSet"}")
    }
}
