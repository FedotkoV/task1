package com.getstarted.lesson1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView

// holds stopping time in milliseconds
private const val STOPPING_TIME: Long = 1000

class MainActivity : AppCompatActivity() {
    lateinit var image: ImageView // will hold reference for ImageView

    // new thread for changing the pics
    private val t: Thread = Thread {
        startingAnimation()
    }

    // holds actual num of the shown image (default is 5)
    private var imageIndex: Int = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image_view)

        if (savedInstanceState != null) {
            imageIndex = savedInstanceState.getInt("imageIndex")
        }

        //  starts the thread
        Handler(Looper.getMainLooper()).postDelayed({
            t.start()
        }, 0)
    }

    override fun onResume() {
        super.onResume()
        // helps to move to the next activity after pause or lost focusing on activity
        if (imageIndex < 2) {
            t.interrupt()
            goToNextActivity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!t.isInterrupted) t.interrupt() // stops the thread if it is run
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("imageIndex", imageIndex) // save current imageIndex
    }
    /**
     * changes the image view after pause($stoppingTime) inside loop
     */
    private fun startingAnimation() {
        for (i in imageIndex downTo 1) {
            imageIndex = i // set new index of image
            val nextImage = when (i) {
                5 -> R.drawable.dice_5
                4 -> R.drawable.dice_4
                3 -> R.drawable.dice_3
                2 -> R.drawable.dice_2
                else -> R.drawable.dice_1
            }
            runOnUiThread {
                image.setImageResource(nextImage)
            }
            if(!t.isInterrupted) {
                try {
                    Thread.sleep(STOPPING_TIME)
                }
                catch (_: InterruptedException){
                    return
                }
            }
        }
        goToNextActivity()
    }

    /**
     * creates a new intent and starts it
     */
    private fun goToNextActivity() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }
}