package com.example.leftoverrecipe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.leftoverrecipe.auxiliaryClasses.Recipe;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Recipe> recipeArray;
    private Context context;

    public RecipesAdapter(Context context, ArrayList<Recipe> recipeArray) {
        this.mInflater = LayoutInflater.from(context);
        this.recipeArray = recipeArray;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) mInflater.inflate(R.layout.item_view, parent, false);
        return new RecipeViewHolder(constraintLayout, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeArray.get(position);
//        System.out.println(position);
//        System.out.println(recipe);
//        System.out.println("----");
        holder.id.setText(recipe.getId());
        holder.url = Uri.parse(recipe.getSourceURL().toString());
        holder.mTitleTextView.setText(recipe.getTitle());
        Glide.with(context).load(recipe.getImageURL()).into(holder.mImageView);
        holder.mServingsSizeTextView.setText(recipe.getServings());
        holder.mPrepTimeTextView.setText(recipe.getPrepTime());
        holder.mDislikeImageView.setOnClickListener(v -> {
            recipeArray.remove(holder.getAdapterPosition());
            notifyItemChanged(holder.getAdapterPosition());
            notifyItemRangeRemoved(holder.getAdapterPosition(), 1);
            if (recipeArray.size() == 0) { Toast.makeText(context, "You deleted everything!", Toast.LENGTH_SHORT).show(); }
        });
    }

    @Override
    public int getItemCount() {
        return recipeArray.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Uri url;
        private ConstraintLayout view;
        private TextView mTitleTextView, mPrepTimeTextView, mServingsSizeTextView, id;
        private ImageView mImageView, mFavouriteImageView, mDislikeImageView;

        public RecipeViewHolder(@NonNull ConstraintLayout itemView, RecipesAdapter recipesAdapter) {
            super(itemView);
            setupViews();
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), ((TextView) view.findViewById(R.id.title_textview)).getText(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, url);

            if (intent.resolveActivity(view.getContext().getPackageManager()) != null)
                startActivity(view.getContext(), intent, null);
            else
                Log.d("ImplicitIntents", "Can't access URL");

        }

        private void setupViews() {
            this.view = itemView.findViewById(R.id.recipeConstraintLayout);
            this.view.setOnClickListener(this);
            this.mTitleTextView = itemView.findViewById(R.id.title_textview);
            this.mPrepTimeTextView = itemView.findViewById(R.id.time_required_textview);
            this.mServingsSizeTextView = itemView.findViewById(R.id.servings_textview);
            this.mImageView = itemView.findViewById(R.id.imageView);
            this.id = itemView.findViewById(R.id.id);
            this.mFavouriteImageView = itemView.findViewById(R.id.favourite_imageView);
            this.mDislikeImageView = itemView.findViewById(R.id.dislike_imageView);
        }
    }
}
