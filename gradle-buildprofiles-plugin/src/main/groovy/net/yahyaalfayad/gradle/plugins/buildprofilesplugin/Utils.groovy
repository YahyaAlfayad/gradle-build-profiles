package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// TODO divide into updateUtils and create utils
final class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class)

    private Project project

    Utils(Project project) {
        this.project = project
    }

    void updateAllFoldersForProfile(final def activeBuildProfile,
                                    final List currentlySpecifiedLanguages) {

        updateAllSourcesForProfile(activeBuildProfile, currentlySpecifiedLanguages)
        updateAllResourcesbyProfile(activeBuildProfile)
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

    void updateTestSourcesForProfile(final def activeBuildProfile,
                                     final List currentlySpecifiedLanguages) {

        updateSourceSetBySourceSetAndProfile('test', activeBuildProfile, currentlySpecifiedLanguages)
    }

    void updateSourceSetBySourceSetAndProfile(final String sourceSet,
                                              final def buildProfile,
                                              final List currentlySpecifiedLanguages) {

        logger.debug("updating ${sourceSet} sources for profile: ${buildProfile}")

        currentlySpecifiedLanguages.forEach { specifiedProgramingLanguage ->

            logger.debug("updating ${sourceSet} sources for profile: ${buildProfile} and progrming language: ${specifiedProgramingLanguage}")
            logger.debug("content of ${sourceSet} sources before update is: ${project.sourceSets."$sourceSet"}")
            logger.debug('adding the following source folder: ' + "src/${sourceSet}/${specifiedProgramingLanguage}-${buildProfile}")
            project.sourceSets."${sourceSet}"."${specifiedProgramingLanguage}".srcDir "src/${sourceSet}/${specifiedProgramingLanguage}-${buildProfile}"
            logger.debug("content of ${sourceSet} sources after update is: ${project.sourceSets."$sourceSet"}")
        }
    }

    void updateAllResourcesbyProfile(final def buildProfile) {
        updateResourcesByProfile('main', buildProfile)
        updateResourcesByProfile('test', buildProfile)
    }

    void updateResourcesByProfile(final String sourceSet,
                                  final def buildProfile) {

        logger.debug("updating ${sourceSet} resources for profile: ${buildProfile}")

        project.sourceSets."${sourceSet}".resources.srcDir "src/${sourceSet}/resources-${buildProfile}"
        logger.debug("content of ${sourceSet} resources after update is: ${project.sourceSets."$sourceSet"}")

    }

    void createProfileMainSources(final String profileName,
                                  final List currentlySpecifiedLanguages) {

        createFolderByBasePathAndProfile('src/main/', profileName, currentlySpecifiedLanguages)
    }

    void createFolderByBasePathAndProfile(final String basePath,
                                          final String profileName,
                                          final List currentlySpecifiedLanguages) {

        currentlySpecifiedLanguages.forEach { specifiedProgramingLanguage ->

            if (project.sourceSets.main.hasProperty("${specifiedProgramingLanguage}")) {

                def folderToCreate = project.file("${basePath}${specifiedProgramingLanguage}-${profileName}")

                if (!folderToCreate.exists()) {
                    folderToCreate.mkdirs()
                }
            }
        }
    }

    void createProfileTestSources(final String profileName, final List specifiedProgrammingLanguages) {

        createFolderByBasePathAndProfile('src/test/', profileName, specifiedProgrammingLanguages)
    }

    void createProfileMainResources(final def buildProfile) {

        createResourcesFolderBySourceSetAndProfile('main', buildProfile)
    }

    void createProfileTestResources(final def buildProfile) {

        createResourcesFolderBySourceSetAndProfile('test', buildProfile)
    }

    void createResourcesFolderBySourceSetAndProfile(final String sourceSet, final def buildProfile) {

        def resourcesFolder = project.file("src/${sourceSet}/resources-${buildProfile}")

        if (!resourcesFolder.exists()) {
            resourcesFolder.mkdirs()
        }
    }
}