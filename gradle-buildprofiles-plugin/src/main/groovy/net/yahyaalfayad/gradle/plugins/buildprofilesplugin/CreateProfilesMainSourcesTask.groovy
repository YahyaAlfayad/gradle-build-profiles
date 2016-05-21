package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.tasks.TaskAction

class CreateProfilesMainSourcesTask extends AbstractBuildProfileTask {

    @TaskAction
    def action() {
        buildProfilesConfig.with {
            buildProfiles.forEach { activeBuildProfile ->
                utils.createProfileMainSources(activeBuildProfile, programingLanguages)
            }
        }
    }
}