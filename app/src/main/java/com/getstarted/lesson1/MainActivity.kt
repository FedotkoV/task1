package com.getstarted.lesson1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

// holds stopping time in milliseconds
private const val STOPPING_TIME: Long = 1000

class MainActivity : AppCompatActivity() {
    lateinit var image: ImageView // will hold reference for ImageView

    // new thread for changing the pics
    lateinit var myThread: CoroutineScope

    // holds actual num of the shown image (default is 5)
    private var imageIndex: Int = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image_view)

        if (savedInstanceState != null) {
            imageIndex = savedInstanceState.getInt("imageIndex")
        }
    }

    override fun onStart() {
        super.onStart()
        //  starts the thread
        myThread = CoroutineScope(Main)
        myThread.launch { startingAnimation() }
    }

    override fun onPause() {
        super.onPause()
        myThread.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("imageIndex", imageIndex) // save current imageIndex
    }
    /**
     * changes the image view after pause($stoppingTime) inside loop
     */
    private suspend fun startingAnimation() {
        for (i in imageIndex downTo 1) {
            imageIndex = i // set new index of image
            val nextImage = when (i) {
                5 -> R.drawable.dice_5
                4 -> R.drawable.dice_4
                3 -> R.drawable.dice_3
                2 -> R.drawable.dice_2
                else -> R.drawable.dice_1
            }
            setImage(nextImage)
            delay(STOPPING_TIME)
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

    private suspend fun setImage(input: Int) {
        withContext(Main) {
            image.setImageResource(input)
        }
    }
}