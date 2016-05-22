package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks

import org.gradle.api.tasks.TaskAction

class CreateProfilesMainSourcesTask extends AbstractBuildProfileCreateTask {

    @TaskAction
    def action() {
        buildProfilesConfig.with {
            buildProfiles.forEach { activeBuildProfile ->
                createUtils.createProfileMainSources(activeBuildProfile, programingLanguages)
            }
        }
    }
}