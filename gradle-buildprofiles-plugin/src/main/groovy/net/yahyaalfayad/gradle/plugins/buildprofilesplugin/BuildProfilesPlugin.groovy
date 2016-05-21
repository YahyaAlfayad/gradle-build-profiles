package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BuildProfilesPlugin implements Plugin<Project> {

    private static Logger logger = LoggerFactory.getLogger(BuildProfilesPlugin.class)

    @Override
    void apply(Project project) {

        logger.debug('applying build profiles plugin')

        logger.debug('creating build profiles extension object')
        project.extensions.create('buildProfilesConfig', BuildProfilesConfig, project)

        logger.debug('creating createProfilesMainSources task')
        project.tasks.create('createProfilesMainSources', CreateProfilesMainSourcesTask)

        logger.debug('creating createProfilesMainResources task')
        project.tasks.create('createProfilesMainResources', CreateProfilesMainResourcesTask)

        logger.debug('creating createProfilesTestSources task')
        project.tasks.create('createProfilesTestSources', CreateProfilesTestSourcesTest)

        logger.debug('creating createProfilesTestResources task')
        project.tasks.create('createProfilesTestResources', CreateProfilesTestResourcesTask)

        logger.debug('creating createProfiles task')
        project.tasks.create('createProfiles').configure {

            dependsOn << ['createProfilesMainSources',
                          'createProfilesMainResources',
                          'createProfilesTestSources',
                          'createProfilesTestResources']
        }
    }
}