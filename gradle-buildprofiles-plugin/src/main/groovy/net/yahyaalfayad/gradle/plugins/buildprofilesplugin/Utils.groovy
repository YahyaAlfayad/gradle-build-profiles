package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Project

final class Utils {

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

        currentlySpecifiedLanguages.forEach { specifiedProgramingLanguage ->

            if (sourceSets.main.hasProperty("${specifiedProgramingLanguage}")) {

                project.sourceSets."${sourceSet}"."${specifiedProgramingLanguage}".srcDir "src/${sourceSet}/${folderBaseName}${specifiedProgramingLanguage}-${buildProfile}"
            }
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