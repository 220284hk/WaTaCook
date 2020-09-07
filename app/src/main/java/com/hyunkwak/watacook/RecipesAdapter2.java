//package com.example.leftoverrecipe;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.leftoverrecipe.auxiliaryClasses.Recipe;
//import com.example.leftoverrecipe.auxiliaryClasses.User;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static androidx.core.content.ContextCompat.startActivity;
//
//public class RecipesAdapter2 extends RecyclerView.Adapter<RecipesAdapter2.RecipeViewHolder> {
//    private LayoutInflater mInflater;
//    private HashMap<String, Recipe> recipeMap;
//    private Context context;
//
//    public RecipesAdapter2(Context context, HashMap<String, Recipe> recipeMap) {
//        this.mInflater = LayoutInflater.from(context);
//        this.recipeMap = recipeMap;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ConstraintLayout constraintLayout = (ConstraintLayout) mInflater.inflate(R.layout.item_view, parent, false);
//        return new RecipeViewHolder(constraintLayout, this);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
//        Recipe recipe = recipeArray.get(position);
//        holder.id.setText(recipe.getId());
//        holder.url = Uri.parse(recipe.getSourceUrl().toString());
//        holder.mTitleTextView.setText(recipe.getTitle());
//        Glide.with(context).load(recipe.getImageURL()).into(holder.mImageView);
//        holder.mServingsSizeTextView.setText(recipe.getServings());
//        holder.mPrepTimeTextView.setText(recipe.getPrepTime());
//        holder.mDislikeImageView.setOnClickListener(v -> {
//            recipeArray.remove(holder.getAdapterPosition());
//            notifyItemChanged(holder.getAdapterPosition());
//            notifyItemRangeRemoved(holder.getAdapterPosition(), 1);
//            TextView titleTextView = holder.mTitleTextView;
////            User.getDislikesSet().add(recipe);
//            User.getDislikesMap().put(recipe.getId(), recipe);
//            Toast.makeText(context, titleTextView.getText().toString() + recipe.getId() + " has been disliked. This will not be shown in future searches!", Toast.LENGTH_SHORT).show();
//            if (recipeArray.size() == 0) {
//                Toast.makeText(context, "You deleted everything!", Toast.LENGTH_SHORT).show();
//                ((Activity) context).finish();
//            }
//        });
//        holder.mLikesImageView.setOnClickListener(v -> {
//            if ( ((ImageView) v).getContentDescription() == null || ((ImageView) v).getContentDescription().equals("not filled")) {
////                User.getLikesSet().add(recipe);
//                User.getLikesMap().put(recipe.getId(), recipe);
//                ((ImageView) v).setImageResource(R.drawable.favourite_post_icon);
//                ((ImageView) v).setContentDescription(FILLED);
//                Toast.makeText(context, recipe.getTitle() + recipe.getId() + " has been liked. It will appear in the favourites page!", Toast.LENGTH_SHORT).show();
//            } else {
//                v.setContentDescription(context.getResources().getString(R.string.filled));
//                ((ImageView) v).setImageResource(R.drawable.favourite_pre_icon);
//                User.getDislikesMap().remove(recipe.getId());
////                User.getLikesSet().remove(recipe);
////                ((ImageView) v).setImageDrawable(context.getResources().getDrawable(R.drawable.favourite_post_icon));
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return recipeArray.size();
//    }
//
//    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private Uri url;
//        private ConstraintLayout view;
//        private TextView mTitleTextView, mPrepTimeTextView, mServingsSizeTextView, id;
//        private ImageView mImageView, mLikesImageView, mDislikeImageView;
//        public RecipeViewHolder(@NonNull ConstraintLayout itemView, RecipesAdapter2 recipesAdapter) {
//            super(itemView);
//            setupViews();
//        }
//
//        @Override
//        public void onClick(View view) {
//            Toast.makeText(view.getContext(), ((TextView) view.findViewById(R.id.title_textview)).getText(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Intent.ACTION_VIEW, url);
//
//            if (intent.resolveActivity(view.getContext().getPackageManager()) != null)
//                startActivity(view.getContext(), intent, null);
//            else
//                Log.d("ImplicitIntents", "Can't access URL");
//        }
//
//        private void setupViews() {
//            this.view = itemView.findViewById(R.id.recipeConstraintLayout);
//            this.view.setOnClickListener(this);
//            this.mTitleTextView = itemView.findViewById(R.id.title_textview);
//            this.mPrepTimeTextView = itemView.findViewById(R.id.time_required_textview);
//            this.mServingsSizeTextView = itemView.findViewById(R.id.servings_textview);
//            this.mImageView = itemView.findViewById(R.id.imageView);
//            this.id = itemView.findViewById(R.id.id);
//            this.mLikesImageView = itemView.findViewById(R.id.likes_imageView);
//            this.mDislikeImageView = itemView.findViewById(R.id.dislike_imageView);
//        }
//    }
//}
