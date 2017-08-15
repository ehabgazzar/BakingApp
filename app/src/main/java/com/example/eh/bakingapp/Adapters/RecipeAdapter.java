package com.example.eh.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eh.bakingapp.R;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.models.StepItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eh on 6/24/2017.
 */

public class RecipeAdapter extends  RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final Context recipeContext;


    private final RecipeItem recipeItem = new RecipeItem();

    private List<RecipeItem> RecipeItemObjects;
    CustomItemClickListener listener;

    public RecipeAdapter(Context context, List<RecipeItem> recipeItemObjects,CustomItemClickListener listener) {
        this.recipeContext = context;
        RecipeItemObjects = recipeItemObjects;
        this.listener=listener;

    }



    public Context getContext() {
        return recipeContext;
    }

    public void add(RecipeItem object) {
        synchronized (recipeItem) {
            RecipeItemObjects.add(object);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (recipeItem) {
            RecipeItemObjects.clear();
        }
        notifyDataSetChanged();
    }

    public void setData(List<RecipeItem> data) {
        clear();
        for (RecipeItem recipeItem : data) {
            add(recipeItem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(itemView);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, myViewHolder.getAdapterPosition());
            }
        });

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RecipeItem recipeItem = getItem(position);

        holder.nameView.setText(recipeItem.getName());
        String image_url =recipeItem.getImage();
        if(!image_url.isEmpty()) {
            Picasso.with(getContext()).load(image_url).into(holder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return RecipeItemObjects.size();
    }
    public  RecipeItem getItem(int position) {
        return RecipeItemObjects.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.recipe_name)TextView nameView;
        @BindView(R.id.grid_item_image)ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
          //  nameView = (TextView) view.findViewById(R.id.recipe_name);
        }
    }
}
