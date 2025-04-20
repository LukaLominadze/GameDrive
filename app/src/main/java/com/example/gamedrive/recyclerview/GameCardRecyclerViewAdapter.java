package com.example.gamedrive.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamedrive.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GameCardRecyclerViewAdapter extends RecyclerView.Adapter<GameCardRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    private Context context;
    private ArrayList<GameCardModel> gameCardModels;

    public GameCardRecyclerViewAdapter(RecyclerViewInterface recyclerViewInterface, Context context, ArrayList<GameCardModel> gameCardModels) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.gameCardModels = gameCardModels;
    }

    @NonNull
    @Override
    public GameCardRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_game_row, parent, false);
        return new GameCardRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GameCardRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.TitleTextView.setText(gameCardModels.get(position).getTitle());
        holder.CategoryTextView.setText(gameCardModels.get(position).getCategory());
        Picasso.get().load(gameCardModels.get(position).getImage()).into(holder.CoverImageView);
    }

    @Override
    public int getItemCount() {
        return gameCardModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView TitleTextView;
        public TextView CategoryTextView;
        public ImageView CoverImageView;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            TitleTextView = itemView.findViewById(R.id.rowTitleTextView);
            CategoryTextView = itemView.findViewById(R.id.rowCategoryTextView);
            CoverImageView = itemView.findViewById(R.id.coverImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onClick(position);
                        }
                    }
                }
            });
        }
    }
}