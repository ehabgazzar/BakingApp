package com.example.eh.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eh.bakingapp.R;
import com.example.eh.bakingapp.models.RecipeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eh on 6/24/2017.
 */

public class RecipeAdapter extends BaseAdapter {

    private final Context recipeContext;
    private final LayoutInflater recipeInflater;

    private final RecipeItem recipeItem = new RecipeItem();

    private List<RecipeItem> RecipeItemObjects;

    public RecipeAdapter(Context context, List<RecipeItem> recipeItemObjects) {
        this.recipeContext = context;
        this.recipeInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RecipeItemObjects = recipeItemObjects;
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
    public int getCount() {
        return RecipeItemObjects.size();
    }

    @Override
    public  RecipeItem getItem(int position) {
        return RecipeItemObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = recipeInflater.inflate(R.layout.recipe_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        final RecipeItem recipeItem = getItem(position);
        viewHolder = (ViewHolder) view.getTag();
        viewHolder.nameView.setText(recipeItem.getName());
        return view;
    }

    public static class ViewHolder {


        @BindView(R.id.recipe_name)TextView nameView;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
            nameView = (TextView) view.findViewById(R.id.recipe_name);
        }
    }
}
