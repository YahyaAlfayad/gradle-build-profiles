package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils

/**
 * @author Yahya Al Fayad
 */
class SourceSetGraphCreator {

    private final List callTrace
    private final int level
    private final Closure methodCallback
    private final Closure propertyCallback

    SourceSetGraphCreator(final Closure methodCallback, final Closure propertyCallback) {
        this.level = 1
        this.callTrace = []
        this.methodCallback = methodCallback ?: { a, b, c -> }
        this.propertyCallback = propertyCallback ?: { a, b, c -> }
    }

    // Recursive constructor used for Depth First Search in the nested closure structure
    private SourceSetGraphCreator(
            final List callTrace,
            final int level,
            final Closure methodCallback,
            final Closure propertyCallback) {

        this.callTrace = callTrace
        this.level = level
        this.methodCallback = methodCallback
        this.propertyCallback = propertyCallback
    }

    void handleClosure(final Closure c) {
        delegateClosureToClass(c, this)
        callTrace.clear()
    }

    static void delegateClosureToClass(final Closure closure, final def classInstance) {
        Closure delegatedClosure = closure.rehydrate(classInstance, this, this)
        delegatedClosure.resolveStrategy = Closure.DELEGATE_ONLY
        delegatedClosure()
    }

    def methodMissing(final String name, final def args) {
        if (args[0] in Closure) {
            clearPreviousCalls()
            callTrace << new Tuple(name, level)
            delegateClosureToClass(args[0], new SourceSetGraphCreator(callTrace, level + 1, methodCallback, propertyCallback))
        } else {
            methodCallback(callTrace, name, args)
            clearPreviousCalls()
        }
    }

    def propertyMissing(final String name, def value) {
        propertyCallback(callTrace, name, value)
        clearPreviousCalls()
    }

    private void clearPreviousCalls() {
        while (callTrace.size() > 0 && level <= callTrace.last()[1]) {
            callTrace.pop()
        }
    }
}
