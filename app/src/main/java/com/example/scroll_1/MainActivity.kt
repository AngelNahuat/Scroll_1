package com.example.scroll_1

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.scroll_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var grabar: TextView? = null
    private val RECOGNIZE_SPEECH_ACTIVITY = 1
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        grabar = findViewById(R.id.txtMostrar)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        fun onActivityResult(
            requestCode: Int, resultCode: Int,
            data: Intent?
        ) {
            super.onActivityResult(requestCode, resultCode, data)
            when (requestCode) {
                RECOGNIZE_SPEECH_ACTIVITY ->
                    if (resultCode == Activity.RESULT_OK && null != data) {
                        val speech = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        var strSpeech2Text = speech?.get(0)
                        if (strSpeech2Text == "baja" || strSpeech2Text == "Baja"){
                            grabar?.setText("Texto reconocido: "+strSpeech2Text)
                        }
                        else{
                        grabar?.setText(strSpeech2Text)
                    }}

                else -> {
                }
            }
        }

        fun hablar(v: View) {
            val intentActionRecognizeSpeech = Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            )
            // Configura el Lenguaje (Español-México)
            intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX"
            )
            try {
                startActivityForResult(
                    intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY
                )
            } catch (a: ActivityNotFoundException) {
                Toast.makeText(
                    applicationContext,
                    "Tú dispositivo no soporta el reconocimiento por voz",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}