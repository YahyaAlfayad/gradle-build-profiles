package net.yahyaalfayad.gradle.plugins.buildprofilesplugin
/**
 * @author Yahya Al Fayad
 */
class SingleBuildProfileConfig {

    String name
    boolean active = true

    private Closure dependencies
    private Closure repositories
    private Closure sourceSets

    void dependencies(final Closure deps) {
        this.dependencies = deps
    }

    void repositories(final Closure repos) {
        this.repositories = repos
    }

    void sourceSets(final Closure sources) {
        this.sourceSets = sources
    }

    @Override
    public String toString() {
        return name;
    }
}
