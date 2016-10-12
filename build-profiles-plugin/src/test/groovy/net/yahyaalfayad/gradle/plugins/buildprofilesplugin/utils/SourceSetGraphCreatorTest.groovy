package net.yahyaalfayad.gradle.plugins.buildprofilesplugin.utils

import groovy.transform.Canonical
import org.junit.Test

import static org.gradle.internal.impldep.org.testng.Assert.assertEquals


/**
 * @author Yahya Al Fayad
 */
class SourceSetGraphCreatorTest {

    final private Closure testClosure = {
        main {
            java {
                blah {
                    srcDir 'whatever'
                }
            }
            groovy {

            }
        }
        test {
            java {
            }
            groovy {
                // error when using srcDirs ['a', 'b']. Groovy consider that as property instead of method call
                srcDirs = ['a', 'b']
            }
        }
    }

    @Test
    void 'test if method callback is executed for leaf nodes only'() {

        List expectedResults = [new TestResult([['main', 1], ['java', 2], ['blah', 3]], 'srcDir', ['whatever'])]
        List actualResults = []

        SourceSetGraphCreator sourceSetHandler = new SourceSetGraphCreator({ List callTrace,
                                                                             String name = null,
                                                                             def args = null ->
            actualResults << new TestResult(callTrace.collect(), name, args)
        }, null)

        sourceSetHandler.handleClosure testClosure

        assertEquals(actualResults, expectedResults)
    }

    @Test
    void "test if property callback executed for properties only"() {
        List expectedResults = [new TestResult([['test', 1], ['groovy', 2]], 'srcDirs = ', ['a', 'b'])]
        List actualResults = []

        SourceSetGraphCreator sourceSetHandler = new SourceSetGraphCreator(null, { List callTrace,
                                                                                   String name = null,
                                                                                   def args = null ->
            actualResults << new TestResult(callTrace.collect(), "$name = ", args)
        })

        sourceSetHandler.handleClosure testClosure

        assertEquals(actualResults, expectedResults)
    }

    @Canonical
    class TestResult {
        List callTrace
        String name
        def args
    }
}
