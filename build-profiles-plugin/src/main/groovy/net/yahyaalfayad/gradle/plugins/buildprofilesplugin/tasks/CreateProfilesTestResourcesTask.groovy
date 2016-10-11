package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks

import org.gradle.api.tasks.TaskAction

class CreateProfilesTestResourcesTask extends AbstractBuildProfileCreateTask {

    @TaskAction
    def action() {

        buildProfilesConfig.with {
            profiles.values().forEach { activeBuildProfile ->
                createUtils.createProfileTestResources(activeBuildProfile)
            }

        }
    }
}
