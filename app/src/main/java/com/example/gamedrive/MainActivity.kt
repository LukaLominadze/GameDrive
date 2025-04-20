package com.example.gamedrive

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamedrive.api.Game
import com.example.gamedrive.api.RestClient
import com.example.gamedrive.recyclerview.GameCardModel
import com.example.gamedrive.recyclerview.GameCardRecyclerViewAdapter
import com.example.gamedrive.recyclerview.RecyclerViewInterface
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class MainActivity : AppCompatActivity(), RecyclerViewInterface {
    private lateinit var gameCardModels: ArrayList<GameCardModel>
    private lateinit var gameArray: ArrayList<Game>

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
        RestClient.init()

        lifecycleScope.launch {
            try {
                val response = RestClient.getGameApi().getGames()
                if (response.isSuccessful)  {
                    val games = response.body()
                    games?.forEach { game ->
                        var categories = game.categories?.get(0)?.name
                        gameArray = ArrayList<Game>()
                        for (g in games) {
                            gameArray.add(Game(
                                id = g.id,
                                name = g.name,
                                description = g.description,
                                image = g.image,
                                categories = g.categories
                            ))
                        }
                        for (i in 1..game.categories!!.size-1) {
                            categories += (", " + game.categories[i].name)
                        }
                        gameCardModels.add(GameCardModel(game.name, categories, game.image))
                     }
                }

                val recyclerView = findViewById<RecyclerView>(R.id.NoteRecyclerView)
                val recyclerViewAdapter = GameCardRecyclerViewAdapter(this@MainActivity, this@MainActivity, gameCardModels)
                recyclerView.adapter = recyclerViewAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Invalid request", Toast.LENGTH_SHORT).show()
            } catch (e: HttpException) {
                Toast.makeText(this@MainActivity, "Http Exception", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }



        /*val addNoteButton = findViewById<Button>(R.id.addNoteCardButton)
        addNoteButton.setOnClickListener {
            val newNote = NoteCardModel("New Note " + noteCardModels.size.toString(), "This is a dynamically added note.")
            noteCardModels.add(newNote)
            recyclerViewAdapter.notifyItemInserted(noteCardModels.size - 1)
            recyclerView.scrollToPosition(noteCardModels.size - 1)
        }*/
    }

    override fun onClick(position: Int) {

    }
}