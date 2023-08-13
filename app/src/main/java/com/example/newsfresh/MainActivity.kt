package com.example.newsfresh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.view.animation.AnimationUtils
import android.os.Handler

class MainActivity : AppCompatActivity() {
    private lateinit var dot1: TextView
    private lateinit var dot2: TextView
    private lateinit var dot3: TextView
    private val handler = Handler()
    private val dotBlinkInterval = 1000 // Adjust the interval as needed
    private val redirectionDelay = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dot1 = findViewById(R.id.dot1)
        dot2 = findViewById(R.id.dot2)
        dot3 = findViewById(R.id.dot3)

        // Start the blinking animation sequence
        startBlinkingAnimation()
        handler.postDelayed({
            val intent = Intent(this@MainActivity, NewsListActivity::class.java)
            startActivity(intent)
            finish() // Optional: Finish the current activity to prevent back navigation
        }, redirectionDelay.toLong())
    }

    private fun startBlinkingAnimation() {
        val blinkAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.blink_animation)

        dot1.startAnimation(blinkAnimation)
        handler.postDelayed({
            dot2.startAnimation(blinkAnimation)
        }, dotBlinkInterval.toLong())

        handler.postDelayed({
            dot3.startAnimation(blinkAnimation)
        }, (dotBlinkInterval * 2).toLong())
    }


}