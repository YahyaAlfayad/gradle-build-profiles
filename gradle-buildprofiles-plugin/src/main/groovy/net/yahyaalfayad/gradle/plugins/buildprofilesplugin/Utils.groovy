package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// TODO devide into updateUtils and ceate utils
// TODO add update testSourceSets for profile
final class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class)

    private Project project

    Utils(Project project) {
        this.project = project
    }

    void updateMainSourcesForProfile(final def activeBuildProfile,
                                     final List currentlySpecifiedLanguages) {

        updateSourceSetBySourceSetAndProfile('main', '', activeBuildProfile, currentlySpecifiedLanguages)
    }

    void updateSourceSetBySourceSetAndProfile(final String sourceSet,
                                              final String folderBaseName,
                                              final def buildProfile,
                                              final List currentlySpecifiedLanguages) {

        logger.debug("updating ${sourceSet} for profile: ${buildProfile}")

        currentlySpecifiedLanguages.forEach { specifiedProgramingLanguage ->

            logger.debug("updating ${sourceSet} for profile: ${buildProfile} and progrming language: ${specifiedProgramingLanguage}")
            logger.debug("content of ${sourceSet} sources before update is: ${project.sourceSets."$sourceSet"}")
            logger.debug('adding the following source folder: ' + "src/${sourceSet}/${specifiedProgramingLanguage}-${buildProfile}")
            project.sourceSets."${sourceSet}"."${specifiedProgramingLanguage}".srcDir "src/${sourceSet}/${specifiedProgramingLanguage}-${buildProfile}"
            logger.debug("content of ${sourceSet} sources after update is: ${project.sourceSets."$sourceSet"}")
        }
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

    void createProfileTestResources(final def buildProfile) {

        createResourcesFolderBySourceSetAndPRofile('test', buildProfile)
    }

    void createResourcesFolderBySourceSetAndPRofile(final String sourceSet, final def buildProfile) {

        def resourcesFolder = project.file("src/${sourceSet}/resources-${buildProfile}")

        if (!resourcesFolder.exists()) {
            resourcesFolder.mkdirs()
        }
    }
}