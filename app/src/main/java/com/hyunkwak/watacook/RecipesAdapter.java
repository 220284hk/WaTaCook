package com.hyunkwak.watacook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hyunkwak.watacook.auxiliaryClasses.Recipe;
import com.hyunkwak.watacook.auxiliaryClasses.User;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.FAVOURITES;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.FILLED;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.NOT_FILLED;
import static com.hyunkwak.watacook.auxiliaryClasses.Strings.SEARCH;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Recipe> recipeArray;
    private Context context;
    private Integer TYPE;

    public RecipesAdapter(Context context, ArrayList<Recipe> recipeArray, Integer TYPE) {
        this.mInflater = LayoutInflater.from(context);
        this.recipeArray = recipeArray;
        this.context = context;
        this.TYPE = TYPE;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout;
        if (TYPE == SEARCH) {
            constraintLayout = (ConstraintLayout) mInflater.inflate(R.layout.item_view, parent, false);
            return new RecipeViewHolder(constraintLayout, this);
        } else {
            CardView cardView = (CardView) mInflater.inflate(R.layout.card_view, parent, false);
            return new RecipeViewHolder(cardView, this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeArray.get(position);
        holder.url = Uri.parse(recipe.getSourceUrl());
        holder.mTitleTextView.setText(recipe.getTitle());
        Glide.with(context).load(recipe.getImageURL()).into(holder.mImageView);
//        Log.d(TAG, holder.mImageView.toString());
        if (TYPE == SEARCH) {
            holder.id.setText(recipe.getId());
            holder.mServingsSizeTextView.setText(recipe.getServings());
            holder.mPrepTimeTextView.setText(recipe.getPrepTime());
            if (User.getInstance() != null) {
                // Likes recall
                if (User.getLikesMap().containsKey(recipe.getId())) {
                    holder.mLikesImageView.setImageResource(R.drawable.favourite_post_icon);
                    holder.mLikesImageView.setContentDescription(FILLED);
                }
                holder.mLikesImageView.setOnClickListener(v -> {
//                    Favourites toggle
                    if (((ImageView) v).getContentDescription() == null || ((ImageView) v).getContentDescription().equals(NOT_FILLED)) {
                        User.getLikesMap().put(recipe.getId(), recipe);
                        ((ImageView) v).setImageResource(R.drawable.favourite_post_icon);
                        ((ImageView) v).setContentDescription(FILLED);
                        Toast.makeText(context, recipe.getTitle() + " has been liked. It will appear in the favourites page!", Toast.LENGTH_SHORT).show();
                    } else {
                        v.setContentDescription(NOT_FILLED);
                        ((ImageView) v).setImageResource(R.drawable.favourite_pre_icon);
                        User.getLikesMap().remove(recipe.getId());
                    }
                });
                holder.mDislikeImageView.setOnClickListener(v -> {
                    recipeArray.remove(holder.getAdapterPosition());
                    notifyItemChanged(holder.getAdapterPosition());
                    notifyItemRangeRemoved(holder.getAdapterPosition(), 1);
                    TextView titleTextView = holder.mTitleTextView;
                    User.getDislikesMap().put(recipe.getId(), recipe);
                    User.getLikesMap().remove(recipe.getId());
                    Toast.makeText(context, titleTextView.getText().toString() + " has been disliked. This will not be shown in future searches!", Toast.LENGTH_SHORT).show();
                    if (recipeArray.size() == 0) {
                        Toast.makeText(context, "You deleted everything!", Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                    }
                });
            }
        } else if (TYPE == FAVOURITES) {
            holder.shareIcon.setOnClickListener(v -> {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
//                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, recipe.getTitle() + " recipe");
                share.putExtra(Intent.EXTRA_TEXT, "Hey, check this recipe out!\n\n" + recipe.getSourceUrl());
                context.startActivity(Intent.createChooser(share, "Share link to..."));
            });
            holder.mDislikeImageView.setOnClickListener(v -> {
                recipeArray.remove(holder.getAdapterPosition());
                notifyItemChanged(holder.getAdapterPosition());
                notifyItemRangeRemoved(holder.getAdapterPosition(), 1);
                TextView titleTextView = holder.mTitleTextView;
                User.getDislikesMap().put(recipe.getId(), recipe);
                User.getLikesMap().remove(recipe.getId());
                Toast.makeText(context, titleTextView.getText().toString() + " has been disliked. This will not be shown in future searches!", Toast.LENGTH_SHORT).show();
            });
        }
    }


    @Override
    public int getItemCount() {
        return recipeArray.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Uri url;
        private ConstraintLayout view;
        private CardView cardView;
        private TextView mTitleTextView, mPrepTimeTextView, mServingsSizeTextView, id;
        private ImageView mImageView, mLikesImageView, mDislikeImageView, shareIcon;

        public RecipeViewHolder(@NonNull ConstraintLayout itemView, RecipesAdapter recipesAdapter) {
            super(itemView);
            setupViews();

        }


        public RecipeViewHolder(@NonNull CardView cardView, RecipesAdapter recipesAdapter) {
            super(cardView);
            setupViews();

//            FOR FUTURE VERSIONS
//            cardView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//                @Override
//                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                    MenuItem delete = menu.add("Delete");
//                    System.out.println(menu.toString() + v + menuInfo);
//                    delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
//                            return true;
//                        }
//                    });
//                }
//            });
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
            this.mTitleTextView = itemView.findViewById(R.id.title_textview);
            this.mImageView = itemView.findViewById(R.id.imageView);
            this.id = itemView.findViewById(R.id.id);
            if (TYPE == SEARCH) {
                this.view = itemView.findViewById(R.id.recipeConstraintLayout);
                this.mPrepTimeTextView = itemView.findViewById(R.id.time_required_textview);
                this.mServingsSizeTextView = itemView.findViewById(R.id.servings_textview);
                this.mLikesImageView = itemView.findViewById(R.id.likes_imageView);
                this.mDislikeImageView = itemView.findViewById(R.id.dislike_imageView);
                this.view.setOnClickListener(this);
            } else if (TYPE == FAVOURITES) {
                this.cardView = itemView.findViewById(R.id.cardView);
                this.shareIcon = itemView.findViewById(R.id.share_icon);
                this.mDislikeImageView = itemView.findViewById(R.id.dislike_imageView);
                this.cardView.setOnClickListener(this);
            }
//            else {
//                this.cardView = itemView.findViewById(R.id.cardView);
//                ((Activity) context).registerForContextMenu(cardView);
//          }

        }


    }


}
