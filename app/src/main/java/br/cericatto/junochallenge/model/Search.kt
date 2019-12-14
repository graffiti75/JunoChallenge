package br.cericatto.junochallenge.model

/**
 * Search.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
data class Search(
    val total_count: Long = 0L,
    val incomplete_results: Boolean = false,
    val items: List<Repo>
)