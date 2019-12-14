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
    const val COMMITS_NUMBER = 5

    const val PAGE = 1
    const val ITEMS_PER_PAGE = 30

    const val TEST_GITHUB_USER = "octocat"
    const val TEST_PUBLIC_REPOS = 8

    // TODO: Just a reminder -> For safety reasons, not hardcoded values.
    const val TEST_LOGIN = "<>"
    const val TEST_PASSWORD = "<>"

    //----------------------------------------------
    // Extras
    //----------------------------------------------

    const val REPO_NAME_EXTRA = "repo_name_extra"
    const val QRCODE_LOGIN_EXTRA: String = "qrcode_login_extra"
    const val USER_PASSWORD_EXTRA = "user_password_extra"
}