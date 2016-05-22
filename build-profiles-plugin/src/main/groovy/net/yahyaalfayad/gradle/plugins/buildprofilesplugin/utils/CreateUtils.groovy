package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils

import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// TODO divide into updateUtils and create updateUtils
final class CreateUtils {

    private static final Logger logger = LoggerFactory.getLogger(CreateUtils.class)

    private Project project

    CreateUtils(Project project) {
        this.project = project
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