package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks

import org.gradle.api.tasks.TaskAction

class CreateProfilesMainResourcesTask extends AbstractBuildProfileCreateTask {

    @TaskAction
    def action() {

        buildProfilesConfig.with {
            profiles.values().forEach { buildProfile ->
                createUtils.createProfileMainResources(buildProfile)
            }
        }
    }
}