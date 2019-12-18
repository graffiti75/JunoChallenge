package br.cericatto.junochallenge

/**
 * AppConfiguration.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
object AppConfiguration {

    //----------------------------------------------
    // General Constants
    //----------------------------------------------

    const val BASE_URL = "https://api.github.com"

    const val FIRST_PAGE = 1
    const val ITEMS_PER_PAGE = 20

    const val TEST_GITHUB_FORKS = "2525"
    const val TEST_GITHUB_USER = "octocat"
    const val TEST_PUBLIC_REPOS = 8

    const val APP_PREFERENCES = "app_preferences"

    const val PREFERENCE_QUERY_STRING = "pref_query_string"
    const val PREFERENCE_TOTAL_COUNT = "pref_total_count"
    const val PREFERENCE_PAGE = "pref_page"

    //----------------------------------------------
    // Extras
    //----------------------------------------------

    const val REPO_ID_POSITION_EXTRA = "repo_id_position_extra"
}