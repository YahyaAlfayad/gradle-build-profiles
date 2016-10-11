package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Project

/**
 * @author Yahya Al Fayad
 */
class SingleBuildProfileConfig {

    // private so user can't modify this in build script
    private final Project project
    String name
    boolean active = true
    Closure dependencies
    Closure repositories
    Closure sourceSets

    SingleBuildProfileConfig(final Project project) {
        this.project = project
    }

//    void dependencies(final Closure deps) {
//        project.dependencies deps
//    }
//
//    void repositories(final Closure repos) {
//        project.repositories repos
//    }
//
//    void sourceSets(final Closure sourceSets) {
//        project.sourceSets sourceSets
//    }
    @Override
    public String toString() {
        return name;
    }
}
