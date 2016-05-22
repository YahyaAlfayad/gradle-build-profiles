package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks

import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.BuildProfilesConfig
import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils.CreateUtils
import org.gradle.api.DefaultTask

abstract class AbstractBuildProfileCreateTask extends DefaultTask {

    BuildProfilesConfig buildProfilesConfig = project.buildProfilesConfig ?: new BuildProfilesConfig()

    protected final CreateUtils createUtils = new CreateUtils(project)
}