package com.example.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    internal lateinit var tapMebutton: Button
    internal lateinit var gameScoreTextview: TextView
    internal lateinit var timeLeftTextView: TextView
    internal var score = 0
    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountDown: Long = 6000
    internal val countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tapMebutton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextview = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        //gameScoreTextview.text = getString(R.string.your_score_s, score.toString()) // para no mostrar el %s se inicializa con cero llamandolo desde los R y reemplazando el %s por score
        resetGame()
        tapMebutton.setOnClickListener{
            view ->
            incrementScore()
        }
    }
    private fun resetGame(){
        score = 0
        gameScoreTextview.text = getString(R.string.your_score_s, score.toString())
        val initialTimeLeft = initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.time_left_s, initialTimeLeft.toString())

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished/1000
                timeLeftTextView.text = getString(R.string.time_left_s, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }
    private fun startGame(){
        countDownTimer.start()
        gameStarted=true
    }
    private fun endGame(){
        Toast.makeText(this, getString(R.string.game_over_message, score.toString()), Toast.LENGTH_LONG)
        resetGame()
    }
    private fun incrementScore(){
        if(!gameStarted) startGame()
        score+=1
        val newScore = getString(R.string.your_score_s, score.toString()) //obtengo el String desde los res y le asigno el valor de Score a %s (conviertiendolo en String)
        gameScoreTextview.text = newScore //agrego el nuevo texto a la vista
    }
}
