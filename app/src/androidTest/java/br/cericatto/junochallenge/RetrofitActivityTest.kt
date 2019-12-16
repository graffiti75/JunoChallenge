package br.cericatto.junochallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.cericatto.junochallenge.rules.OkHttpIdlingResourceRule
import br.cericatto.junochallenge.view.activity.test.RetrofitActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TestActivityTest.kt.
 *
 * @author Rodrigo Cericatto
 * @since September 28, 2019
 */
@RunWith(AndroidJUnit4::class)
class RetrofitActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<RetrofitActivity> = ActivityTestRule(
        RetrofitActivity::class.java)

    @get:Rule
    var okHttpIdlingResourceRule = OkHttpIdlingResourceRule()

    // TODO: https://github.com/square/retrofit/issues/2393

    @Test
    fun givenRetrofitApiCall_whenCallingGetRepos_thenCheckSuccessfullResponse() {
        onView(withId(R.id.github_user_id))
            .check(matches(withText(AppConfiguration.TEST_GITHUB_USER)))
    }
}