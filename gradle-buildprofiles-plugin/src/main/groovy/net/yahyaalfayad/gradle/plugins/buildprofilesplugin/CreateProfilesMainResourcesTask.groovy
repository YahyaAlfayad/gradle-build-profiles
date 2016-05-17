package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.tasks.TaskAction

class CreateProfilesMainResourcesTask extends BuildProfileTask {

    @TaskAction
    def action() {

        buildProfilesConfig.with {
            allProfiles.forEach { activeBuildProfile ->
                utils.reateProfileMainSources(activeBuildProfile, programingLanguages)
            }
        }
    }
}