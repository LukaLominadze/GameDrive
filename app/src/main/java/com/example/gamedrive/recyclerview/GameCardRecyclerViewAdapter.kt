package com.example.gamedrive.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamedrive.R
import com.example.gamedrive.api.Game
import com.squareup.picasso.Picasso

class GameCardRecyclerViewAdapter(
    private val recyclerViewInterface: RecyclerViewInterface,
    private val context: Context,
    private val gameCardModels: ArrayList<GameCardModel>,
    private val gameArray: ArrayList<Game>
) : RecyclerView.Adapter<GameCardRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_view_game_row, parent, false)
        return MyViewHolder(view, recyclerViewInterface)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val gameCardModel = gameCardModels[position]
        holder.titleTextView.text = gameCardModel.title
        holder.categoryTextView.text = gameCardModel.category
        Picasso.get()
            .load(gameCardModel.image)
            .resize(600, 800)
            .into(holder.coverImageView)

        holder.bind(gameArray[position])
    }

    override fun getItemCount(): Int {
        return gameCardModels.size
    }

    fun updateList(newList: ArrayList<GameCardModel>, newGameArray: ArrayList<Game>) {
        gameCardModels.clear()
        gameCardModels.addAll(newList)
        gameArray.clear()
        gameArray.addAll(newGameArray)
        notifyDataSetChanged()
    }

    class MyViewHolder(
        itemView: View,
        private val recyclerInterface: RecyclerViewInterface
    ) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.rowTitleTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.rowCategoryTextView)
        val coverImageView: ImageView = itemView.findViewById(R.id.coverImageView)

        fun bind(game: Game) {
            itemView.setOnClickListener {
                recyclerInterface.onClick(game)
            }
        }
    }
}
