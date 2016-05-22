package net.yahyaalfayad.gradle.plugins

import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.*
import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks.CreateProfilesMainResourcesTask
import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks.CreateProfilesMainSourcesTask
import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks.CreateProfilesTestResourcesTask
import net.yahyaalfayad.gradle.plugins.buildprofilesplugin.tasks.CreateProfilesTestSourcesTest
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class BuildProfilesPluginTest {

    @Test
    void apply() {

        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply BuildProfilesPlugin

        project.with {

            assertTrue(extensions.buildProfilesConfig instanceof BuildProfilesConfig)

            assertTrue(tasks.createProfilesMainSources instanceof CreateProfilesMainSourcesTask)
            assertTrue(tasks.createProfilesMainResources instanceof CreateProfilesMainResourcesTask)
            assertTrue(tasks.createProfilesTestSources instanceof CreateProfilesTestSourcesTest)
            assertTrue(tasks.createProfilesTestResources instanceof CreateProfilesTestResourcesTask)

            assertTrue(tasks.hasProperty('createProfiles'))
        }
    }
}
