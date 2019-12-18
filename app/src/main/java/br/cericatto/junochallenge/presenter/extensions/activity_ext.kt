package br.cericatto.junochallenge.presenter.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.cericatto.junochallenge.AppConfiguration
import br.cericatto.junochallenge.presenter.NavigationUtils
import kotlinx.android.synthetic.main.activity_main.*

//--------------------------------------------------
// Shared Preferences
//--------------------------------------------------

fun AppCompatActivity.getCachedQuery(): String {
    val sharedPreferences = getSharedPreferences(AppConfiguration.APP_PREFERENCES, Context.MODE_PRIVATE)
    return sharedPreferences.getString(AppConfiguration.PREFERENCE_QUERY_STRING, "").toString()
}

fun AppCompatActivity.updateCachedQuery(query: String): String {
    val sharedPreferences = getSharedPreferences(AppConfiguration.APP_PREFERENCES, Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(AppConfiguration.PREFERENCE_QUERY_STRING, query).apply()
    return sharedPreferences.getString(AppConfiguration.PREFERENCE_QUERY_STRING, "").toString()
}

fun AppCompatActivity.getTotalCount(): Int {
    val sharedPreferences = getSharedPreferences(AppConfiguration.APP_PREFERENCES, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(AppConfiguration.PREFERENCE_TOTAL_COUNT, -1)
}

fun AppCompatActivity.updateTotalCount(count: Int): String {
    val sharedPreferences = getSharedPreferences(AppConfiguration.APP_PREFERENCES, Context.MODE_PRIVATE)
    sharedPreferences.edit().putInt(AppConfiguration.PREFERENCE_TOTAL_COUNT, count).apply()
    return sharedPreferences.getInt(AppConfiguration.PREFERENCE_TOTAL_COUNT, -1).toString()
}

fun AppCompatActivity.getPage(): Int {
    val sharedPreferences = getSharedPreferences(AppConfiguration.APP_PREFERENCES, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(AppConfiguration.PREFERENCE_PAGE, 1)
}

fun AppCompatActivity.updatePage(count: Int): String {
    val sharedPreferences = getSharedPreferences(AppConfiguration.APP_PREFERENCES, Context.MODE_PRIVATE)
    sharedPreferences.edit().putInt(AppConfiguration.PREFERENCE_PAGE, count).apply()
    return sharedPreferences.getInt(AppConfiguration.PREFERENCE_PAGE, 1).toString()
}

//--------------------------------------------------
// Visibilities
//--------------------------------------------------

fun AppCompatActivity.setVisibilities(loading: Int, recycler: Int, defaultText: Int) {
    this.id_activity_main__loading.visibility = loading
    this.id_activity_main__recycler_view.visibility = recycler
    this.id_activity_main__default_text.visibility = defaultText
}

//--------------------------------------------------
// Toast
//--------------------------------------------------

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToast(message: Int) {
    Toast.makeText(this, this.getString(message), Toast.LENGTH_LONG).show()
}

//--------------------------------------------------
// Connection
//--------------------------------------------------

@Suppress("DEPRECATION")
fun Context.checkIfHasNetwork(): Boolean {
    var result = false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                }
            }
        }
    }
    return result
}

//--------------------------------------------------
// Activity Transitions
//--------------------------------------------------

fun Context.openActivityExtra(activity: Activity, clazz: Class<*>, key: String, value: Any) {
    val intent = Intent(activity, clazz)
    val extras = getExtra(Bundle(), key, value)
    intent.putExtras(extras)

    activity.startActivity(intent)
    NavigationUtils.animate(activity, NavigationUtils.Animation.GO)
}

fun Context.getExtra(extras: Bundle, key: String, value: Any): Bundle {
    when (value) {
        is String -> extras.putString(key, value)
        is Int -> extras.putInt(key, value)
        is Long -> extras.putLong(key, value)
        is Boolean -> extras.putBoolean(key, value)
    }
    return extras
}