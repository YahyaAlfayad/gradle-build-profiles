package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildProfilesPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create('buildProfilesConfig', BuildProfilesConfig, project)


        project.tasks.create('createProfilesMainSources', CreateProfilesMainSourcesTask)
        project.tasks.create('createProfilesMainResources', CreateProfilesMainResourcesTask)

        project.tasks.create('createProfilesTestSources', CreateProfilesTestSourcesTest)
        project.tasks.create('createProfilesTestResources', CreateProfilesTestResourcesTask)

        project.tasks.create('updateBuildProfilesSourceSets', UpdateBuildProfilesSourceSetsTask)

        project.tasks.create('createProfiles').configure {

            dependsOn << ['createProfilesMainSources',
                          'createProfilesMainResources',
                          'createProfilesTestSources',
                          'createProfilesTestResources',
                          'updateBuildProfilesSourceSets']
        }
    }
}