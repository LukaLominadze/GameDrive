package com.example.gamedrive

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso


class GameActivity : AppCompatActivity() {
    private lateinit var gameCoverImageView: ImageView
    private lateinit var gameTitleTextView: TextView
    private lateinit var gameGenreTextView: TextView
    private lateinit var gameDescriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.game_view_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameCoverImageView = findViewById<ImageView>(R.id.gameCoverImageView)
        gameTitleTextView = findViewById<TextView>(R.id.gameTitleTextView)
        gameGenreTextView = findViewById<TextView>(R.id.gameGenreTextView)
        gameDescriptionTextView = findViewById<TextView>(R.id.gameDescriptionTextView)

        gameTitleTextView.setText(intent.getStringExtra("TITLE"))
        gameGenreTextView.setText(intent.getStringExtra("GENRES"))
        gameDescriptionTextView.setText(intent.getStringExtra("DESCRIPTION"))
        Picasso.get()
            .load(intent.getStringExtra("IMAGE"))
            .resize(800, 1100)
            .into(gameCoverImageView);
    }
}