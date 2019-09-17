package com.example.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    internal lateinit var tapMebutton: Button
    internal lateinit var gameScoreTextview: TextView
    internal lateinit var timeLeftTextView: TextView
    internal var score = 0
    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountDown: Long = 60000
    internal val countDownInterval: Long = 1000

    internal var timeLeftOnTimer: Long = 60000
    //Debbuging
    internal val TAG = MainActivity::class.java.simpleName


    companion object{
        private val SCORE_KEY= "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called. Score is: $score")
        tapMebutton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextview = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        //gameScoreTextview.text = getString(R.string.your_score_s, score.toString()) // para no mostrar el %s se inicializa con cero llamandolo desde los R y reemplazando el %s por score
        //resetGame()
        if(savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        }else{
            resetGame()
        }
        tapMebutton.setOnClickListener{
            view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementScore()
        }
    }

    private fun restoreGame() {
        gameScoreTextview.text = getString(R.string.your_score_s, score.toString())
        val restoredTime = timeLeftOnTimer / 1000
        timeLeftTextView.text=getString(R.string.time_left_s, restoredTime.toString())

        countDownTimer = object: CountDownTimer(timeLeftOnTimer, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                var timeLeft = millisUntilFinished/1000
                timeLeftTextView.text = getString(R.string.time_left_s, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()
        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & time Left: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called.")
    }
    //Añadiendo boton de menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_about) showInfo()
        return true
    }

    private fun showInfo(){
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

    private fun resetGame(){
        score = 0
        gameScoreTextview.text = getString(R.string.your_score_s, score.toString())
        val initialTimeLeft = initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.time_left_s, initialTimeLeft.toString())

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                var timeLeft = millisUntilFinished/1000
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
        //Agregando animacion al Score
        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        gameScoreTextview.startAnimation(blinkAnimation)
    }
}
