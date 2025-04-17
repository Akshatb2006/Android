package com.example.languageexample

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    companion object{
        var selectedLanguage :String = "en"
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(applyLanguage(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val englishButton = findViewById<Button>(R.id.englishBtn)
        val hindiButton = findViewById<Button>(R.id.hindiBtn)
        val bengaliButton = findViewById<Button>(R.id.gujaratiBtn)

        englishButton.setOnClickListener {
            setLanguage("en")
        }

        hindiButton.setOnClickListener {
            setLanguage("hi")
        }

        bengaliButton.setOnClickListener {
            setLanguage("bn")
        }

    }

    private fun setLanguage(language: String) {
        selectedLanguage = language
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun applyLanguage(context: Context): Context {
        val locale = Locale(selectedLanguage)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}