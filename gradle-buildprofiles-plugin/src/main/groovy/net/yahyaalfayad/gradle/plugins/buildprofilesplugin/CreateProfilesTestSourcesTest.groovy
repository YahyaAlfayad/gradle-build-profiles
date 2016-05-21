package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.tasks.TaskAction

class CreateProfilesTestSourcesTest extends AbstractBuildProfileTask {

    @TaskAction
    def action() {

        buildProfilesConfig.with {
            buildProfiles.forEach { activeBuildProfile ->
                utils.createProfileTestSources(activeBuildProfile, programingLanguages)
            }
        }
    }
}