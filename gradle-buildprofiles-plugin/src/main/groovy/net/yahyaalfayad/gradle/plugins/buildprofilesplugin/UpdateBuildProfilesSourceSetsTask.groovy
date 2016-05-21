package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.tasks.TaskAction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UpdateBuildProfilesSourceSetsTask extends BuildProfileTask {

    private static Logger logger = LoggerFactory.getLogger(UpdateBuildProfilesSourceSetsTask.class)

    @TaskAction
    def action() {
        buildProfilesConfig.with {

            logger.debug("eecuting ${getClass().getName()} action")

            logger.debug("active build profiles are: ${getActiveBuildProfiles()}")

            getActiveBuildProfiles().forEach { activeBuildProfile ->
                logger.debug("updating main sources for profile: ${activeBuildProfile}")
                utils.updateMainSourcesForProfile(activeBuildProfile, programingLanguages)
            }
        }
    }
}