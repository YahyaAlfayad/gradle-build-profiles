package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks

import org.gradle.api.tasks.TaskAction

class CreateProfilesMainSourcesTask extends AbstractBuildProfileCreateTask {

    @TaskAction
    def action() {
        buildProfilesConfig.with {
            profiles.values().forEach { buildProfile ->
                createUtils.createProfileMainSources(buildProfile.name, programingLanguages)
            }
        }
    }
}