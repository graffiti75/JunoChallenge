package br.cericatto.junochallenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Repo.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
@Entity
data class Repo(
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,
    var id: String = "82128465",
    var node_id: String = "MDEwOlJlcG9zaXRvcnk4MjEyODQ2NQ==",
    var name: String = "Android",
    var full_name: String = "open-android/Android",
    var private: Boolean = false,
    var owner: Owner = Owner(),
    var stargazers_count : Long = 8679,
    var watchers_count : Long = 8679,
    var forks : Long = 2525,
    var open_issues : Long = 21,
    var score : Double = 119.72391
)