package net.yahyaalfayad.gradle.plugins.buildprofilesplugin

import org.gradle.api.Project

/**
 * @author Yahya Al Fayad
 */
class SingleBuildProfileConfig {

    private final Project project

    String name
    boolean active = true

    private Closure dependencies
    private Closure repositories

    SingleBuildProfileConfig(final Project project) {
        this.project = project
    }

    void dependencies(final Closure deps) {
        this.dependencies = deps
    }

    void repositories(final Closure repos) {
        this.repositories = repos
    }

    @Override
    public String toString() {
        return name;
    }
}
