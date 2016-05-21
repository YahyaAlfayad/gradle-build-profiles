package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.DefaultTask

abstract class AbstractBuildProfileTask extends DefaultTask {

    BuildProfilesConfig buildProfilesConfig = project.buildProfilesConfig ?: new BuildProfilesConfig()

    protected final Utils utils = new Utils(project)
}