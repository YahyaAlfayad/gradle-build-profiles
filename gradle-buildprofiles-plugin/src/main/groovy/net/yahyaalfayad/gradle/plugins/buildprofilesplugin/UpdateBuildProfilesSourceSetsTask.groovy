package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.tasks.TaskAction

class UpdateBuildProfilesSourceSetsTask extends BuildProfileTask {

    @TaskAction
    def action() {
        buildProfilesConfig.with {
            activeProfiles.forEach { activeBuildProfile ->
                utils.updateMainSourcesForProfile(activeBuildProfile, programingLanguages)
            }
        }
    }
}