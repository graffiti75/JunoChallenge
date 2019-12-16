package br.cericatto.junochallenge

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.cericatto.junochallenge.rules.OkHttpIdlingResourceRule
import br.cericatto.junochallenge.view.activity.test.UserActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * TestActivityTest.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 15, 2019
 */
@RunWith(AndroidJUnit4::class)
class UserActivityMockWebServerTest {

    @get:Rule
    var rule = OkHttpIdlingResourceRule()

    private val mockWebServer = MockWebServer()
    private val portNumber = 8080

    private val responseBody = "{ \"login\" : \"${AppConfiguration.TEST_GITHUB_USER}\", " +
            "\"public_repos\" : ${AppConfiguration.TEST_PUBLIC_REPOS} }"

    @Before
    @Throws
    fun setUp() {
        mockWebServer.start(portNumber)
        BaseUrlProvider.baseUrl = mockWebServer.url("/").toString()
        ActivityScenario.launch(UserActivity::class.java)
    }

    @After
    @Throws
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowUserNameCorrectly() {
        val response = MockResponse()
            .setBody(responseBody)
            .setBodyDelay(1, TimeUnit.SECONDS)
        mockWebServer.enqueue(response)

        onView(withId(R.id.github_user_name))
            .check(matches(withText(AppConfiguration.TEST_GITHUB_USER)))
    }
}
