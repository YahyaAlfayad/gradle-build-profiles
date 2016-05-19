package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.tasks.TaskAction

class CreateProfilesMainResourcesTask extends BuildProfileTask {

    @TaskAction
    def action() {

        buildProfilesConfig.with {
            buildProfiles.forEach { activeBuildProfile ->
                utils.createProfileMainSources(activeBuildProfile, programingLanguages)
            }
        }
    }
}