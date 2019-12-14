package br.cericatto.junochallenge.presenter.log

import timber.log.Timber

/**
 * LineNumberDebugTree.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
class LineNumberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }
}