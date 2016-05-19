package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.tasks.TaskAction

class CreateProfilesTestResourcesTask extends BuildProfileTask {

    @TaskAction
    def action() {

        buildProfilesConfig.with {
            buildProfiles.forEach { activeBuildProfile ->
                utils.createProfileTestResources(activeBuildProfile)
            }

        }
    }
}
