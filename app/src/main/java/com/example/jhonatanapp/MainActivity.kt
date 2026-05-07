package com.example.jhonatanapp

import android.app.LocaleManager
import android.os.Bundle
import android.os.LocaleList
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.languageSpinner)
        val btnEnter: Button = findViewById(R.id.btnEnter)

        ArrayAdapter.createFromResource(
            this,
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val localeManager = getSystemService(LocaleManager::class.java)
        val currentLocale = localeManager.applicationLocales.get(0)?.language ?: "en"
        
        val initialPosition = when (currentLocale) {
            "en" -> 0
            "es" -> 1
            "fr" -> 2
            "de" -> 3
            else -> 0
        }
        spinner.setSelection(initialPosition, false)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val languageCode = getLanguageCodeFromPosition(position)
                if (languageCode != currentLocale) {
                    changeLanguage(languageCode)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnEnter.setOnClickListener {
            val selectedPosition = spinner.selectedItemPosition
            val languageCode = getLanguageCodeFromPosition(selectedPosition)
            changeLanguage(languageCode)
        }
    }

    private fun getLanguageCodeFromPosition(position: Int): String {
        return when (position) {
            0 -> "en"
            1 -> "es"
            2 -> "fr"
            3 -> "de"
            else -> "en"
        }
    }

    private fun changeLanguage(languageCode: String) {
        val localeManager = getSystemService(LocaleManager::class.java)
        localeManager.applicationLocales = LocaleList.forLanguageTags(languageCode)
    }
}
