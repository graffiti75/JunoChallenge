package br.cericatto.junochallenge.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.cericatto.junochallenge.R

/**
 * MainActivity.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
