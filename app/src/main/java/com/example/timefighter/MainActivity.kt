package com.example.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    internal lateinit var tapMebutton: Button
    internal lateinit var gameScoreTextview: TextView
    internal lateinit var timeLeftTextView: TextView
    internal var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tapMebutton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextview = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        gameScoreTextview.text = getString(R.string.your_score_s, score.toString()) // para no mostrar el %s se inicializa con cero llamandolo desde los R y reemplazando el %s por score
        tapMebutton.setOnClickListener{
            view ->
            incrementScore()
        }
    }
    private fun incrementScore(){
        score+=1
        val newScore = getString(R.string.your_score_s, score.toString()) //obtengo el String desde los res y le asigno el valor de Score a %s (conviertiendolo en String)
        gameScoreTextview.text = newScore //agrego el nuevo texto a la vista
    }
}
