package br.cericatto.junochallenge.presenter

import android.app.Activity
import br.cericatto.junochallenge.R

/**
 * NavigationUtils.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
object NavigationUtils {
    enum class Animation {
        GO,
        BACK
    }

    fun animate(activity: Activity, animation: Animation) {
        if (animation == Animation.GO) {
            activity.overridePendingTransition(R.anim.open_next, R.anim.close_previous)
        } else {
            activity.overridePendingTransition(R.anim.open_previous, R.anim.close_next)
            activity.finish()
        }
    }
}