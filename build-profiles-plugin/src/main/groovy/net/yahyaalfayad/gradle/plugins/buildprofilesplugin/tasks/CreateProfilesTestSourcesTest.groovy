package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks

import org.gradle.api.tasks.TaskAction

class CreateProfilesTestSourcesTest extends AbstractBuildProfileCreateTask {

    @TaskAction
    def action() {

        buildProfilesConfig.with {
            profiles.values().each { activeBuildProfile ->
                createUtils.createProfileTestSources(activeBuildProfile, programingLanguages)
            }
        }
    }
}