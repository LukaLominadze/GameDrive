package com.example.gamedrive

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamedrive.recyclerview.GameCardModel
import com.example.gamedrive.recyclerview.GameCardRecyclerViewAdapter
import com.example.gamedrive.recyclerview.RecyclerViewInterface

class MainActivity : AppCompatActivity(), RecyclerViewInterface {
    private lateinit var gameCardModels: ArrayList<GameCardModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameCardModels = ArrayList<GameCardModel>()
        setupGameCardModels()

        val recyclerView = findViewById<RecyclerView>(R.id.NoteRecyclerView)
        val recyclerViewAdapter = GameCardRecyclerViewAdapter(this, this, gameCardModels)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*val addNoteButton = findViewById<Button>(R.id.addNoteCardButton)
        addNoteButton.setOnClickListener {
            val newNote = NoteCardModel("New Note " + noteCardModels.size.toString(), "This is a dynamically added note.")
            noteCardModels.add(newNote)
            recyclerViewAdapter.notifyItemInserted(noteCardModels.size - 1)
            recyclerView.scrollToPosition(noteCardModels.size - 1)
        }*/
    }

    private fun setupGameCardModels() {
        gameCardModels.add(GameCardModel("Title", "Category"))
    }

    override fun onClick(position: Int) {

    }
}