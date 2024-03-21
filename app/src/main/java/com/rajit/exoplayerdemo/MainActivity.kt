package com.rajit.exoplayerdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rajit.exoplayerdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            // Video 1
            btnVideo1.setOnClickListener {
                Intent(this@MainActivity, MediaPlayerActivity::class.java).apply {
                    putExtra("url", Constant.VIDEO_URL_1)
                    startActivity(this)
                }
            }

            // Video 2
            btnVideo2.setOnClickListener {
                Intent(this@MainActivity, MediaPlayerActivity::class.java).apply {
                    putExtra("url", Constant.VIDEO_URL_2)
                    startActivity(this)
                }
            }

            // Audio 1
            btnAudio1.setOnClickListener {
                Intent(this@MainActivity, MediaPlayerActivity::class.java).apply {
                    putExtra("url", Constant.AUDIO_URL_2)
                    startActivity(this)
                }
            }

        }

    }
}