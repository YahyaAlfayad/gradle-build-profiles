package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils

import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

final class CreateUtils {

    private static final Logger logger = LoggerFactory.getLogger(CreateUtils.class)

    private Project project

    CreateUtils(Project project) {
        this.project = project
    }

    void createProfileMainSources(final def profile,
                                  final List currentlySpecifiedLanguages) {

        logger.info("Creating main sources for profile $profile and languages $currentlySpecifiedLanguages")

        createFolderByBasePathAndProfile('src/main/', profile, currentlySpecifiedLanguages)
    }

    void createFolderByBasePathAndProfile(final String basePath,
                                          final def profile,
                                          final List currentlySpecifiedLanguages) {

        currentlySpecifiedLanguages.each { specifiedProgramingLanguage ->

            if (project.sourceSets.main.hasProperty("${specifiedProgramingLanguage}")) {

                def folderToCreate = project.file("${basePath}${specifiedProgramingLanguage}-${profile}")

                if (!folderToCreate.exists()) {
                    folderToCreate.mkdirs()
                }
            }
        }
    }

    void createProfileTestSources(final def profile, final List specifiedProgrammingLanguages) {

        createFolderByBasePathAndProfile('src/test/', profile, specifiedProgrammingLanguages)
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