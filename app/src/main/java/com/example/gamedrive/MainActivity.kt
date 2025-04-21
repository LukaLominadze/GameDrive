package com.example.gamedrive

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: GameCardRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initializing the necessary data for the application
        gameCardModels = ArrayList<GameCardModel>()
        searchEditText = findViewById<EditText>(R.id.searchEditText)
        RestClient.init()

        // Launch REST
        lifecycleScope.launch {
            try {
                // Retrieve data of video games to display on screen
                val response = RestClient.getGameApi().getGames()
                if (response.isSuccessful) {
                    val games = response.body()
                    setupAdapaterData(games)
                }
                else {
                    Toast.makeText(this@MainActivity, "Internet is required", Toast.LENGTH_SHORT).show()
                }

                recyclerView = findViewById<RecyclerView>(R.id.NoteRecyclerView)
                recyclerViewAdapter = GameCardRecyclerViewAdapter(
                    this@MainActivity,
                    this@MainActivity,
                    gameCardModels,
                    gameArray
                )
                recyclerView.adapter = recyclerViewAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                setOnSearchTextChanged()
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Invalid request", Toast.LENGTH_SHORT).show()
            } catch (e: HttpException) {
                Toast.makeText(this@MainActivity, "Http Exception", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAdapaterData(games: List<Game>?) {
        // Convert all games into game card types to include in the recycler view
        games?.forEach { game ->
            var categories = game.categories?.get(0)?.name
            gameArray = ArrayList<Game>()
            for (g in games) {
                gameArray.add(
                    Game(
                        id = g.id,
                        name = g.name,
                        description = g.description,
                        image = g.image,
                        categories = g.categories
                    )
                )
            }
            // Put categories in a single string
            for (i in 1..game.categories!!.size - 1) {
                categories += (", " + game.categories[i].name)
            }
            gameCardModels.add(GameCardModel(game.name, categories, game.image))
        }
    }

    // Recreate the view to display search results
    private fun setOnSearchTextChanged() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                recyclerViewAdapter = GameCardRecyclerViewAdapter(
                    this@MainActivity,
                    this@MainActivity,
                    gameCardModels.filter { it.title.contains(query, ignoreCase = true) }.toCollection(
                        ArrayList()),
                    gameArray.filter { it.name!!.contains(query, ignoreCase = true) }.toCollection(
                        ArrayList())
                )
                recyclerView.adapter = recyclerViewAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?, p1: Int, p2: Int, p3: Int
            ) {
            }

        })
    }

    // Once a video game card is picked, open a new window to display full information
    override fun onClick(game: Game)  {
        val intent = Intent(this@MainActivity, GameActivity::class.java)
        // Putting genres inside a single string
        var genres = game.categories!!.get(0).name
        for (i in 1..game.categories!!.size-1) {
            genres += (", " + game.categories[i].name)
        }

        intent.putExtra("TITLE", game.name)
        intent.putExtra("GENRES", genres)
        intent.putExtra("DESCRIPTION", game.description)
        intent.putExtra("IMAGE", game.image)

        startActivity(intent)
    }
}