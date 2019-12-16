package br.cericatto.junochallenge

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.cericatto.junochallenge.rules.OkHttpIdlingResourceRule
import br.cericatto.junochallenge.view.activity.test.RetrofitActivity
import kotlinx.android.synthetic.main.activity_retrofit.*
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
class RetrofitActivityMockWebServerTest {

    @get:Rule
    var rule = OkHttpIdlingResourceRule()

    private val mockWebServer = MockWebServer()
    private val portNumber = 8080

//    private val responseBody = "{ \"login\" : \"${AppConfiguration.TEST_GITHUB_USER}\", " +
//        "\"public_repos\" : ${AppConfiguration.TEST_PUBLIC_REPOS} }"
    private val responseBody = "{ \"total_count\": 950398, \"incomplete_results\": false, \"items\": " +
        "[ { \"id\": 82128465, \"node_id\": \"MDEwOlJlcG9zaXRvcnk4MjEyODQ2NQ==\", \"name\": \"Android\", " +
        "\"full_name\": \"open-android/Android\", \"private\": false, \"owner\": { \"login\": \"open-android\", " +
        "\"id\": 23095877, }, \"stargazers_count\": 8679, \"watchers_count\": 8679, \"forks\": 2525, " +
        "\"open_issues\": 21, \"score\": 119.72391 }, { \"id\": 12544093, \"node_id\": " +
        "\"MDEwOlJlcG9zaXRvcnkxMjU0NDA5Mw==\", \"name\": \"Android\", \"full_name\": " +
        "\"hmkcode/Android\", \"private\": false, \"owner\": { \"login\": \"hmkcode\", \"id\": 3790597, }, " +
        "\"stargazers_count\": 2943, \"watchers_count\": 2943, \"forks\": 3401, \"open_issues\": 47, " +
        "\"score\": 117.87389 } ] }"

    @Before
    @Throws
    fun setUp() {
        mockWebServer.start(portNumber)
        BaseUrlProvider.baseUrl = mockWebServer.url("/").toString()
        ActivityScenario.launch(RetrofitActivity::class.java)
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

        onView(withId(R.id.github_repo_forks))
            .check(matches(withText(AppConfiguration.TEST_GITHUB_FORKS)))
    }
}
