package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils

/**
 * @author Yahya Al Fayad
 */
class GeneralUtils {

    static List commaSeparatedSystemPropertyAsList(final String systemProperty) {

        return System.properties[systemProperty]?.split(',')?.collect {
            it.trim()
        }?.toList()
    }
}
