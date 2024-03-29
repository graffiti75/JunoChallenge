package br.cericatto.junochallenge.rules

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import br.cericatto.junochallenge.presenter.api.OkHttpProvider
import com.jakewharton.espresso.OkHttp3IdlingResource
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * OkHttpIdlingResourceRule.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 15, 2019
 */
class OkHttpIdlingResourceRule: TestRule {

    private val resource : IdlingResource = OkHttp3IdlingResource.create("okhttp", OkHttpProvider.instance)

    override fun apply(base: Statement, description: Description): Statement {
        return object: Statement() {
            override fun evaluate() {
                IdlingRegistry.getInstance().register(resource)
                base.evaluate()
                IdlingRegistry.getInstance().unregister(resource)
            }
        }
    }
}