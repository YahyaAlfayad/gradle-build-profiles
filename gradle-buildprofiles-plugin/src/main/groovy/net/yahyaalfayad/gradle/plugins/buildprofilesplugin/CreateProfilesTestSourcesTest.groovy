package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.tasks.TaskAction

class CreateProfilesTestSourcesTest extends BuildProfileTask {

    @TaskAction
    def action() {

        buildProfilesConfig.with {
            buildProfiles.forEach { activeBuildProfile ->
                utils.createProfileTestSources(activeBuildProfile, programingLanguages)
            }
        }
    }
}