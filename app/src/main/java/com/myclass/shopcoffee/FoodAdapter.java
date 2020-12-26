package com.myclass.shopcoffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private ArrayList<Food> foods;
    private FoodInterFace foodInterFace;

    public FoodAdapter(ArrayList<Food> foods, FoodInterFace foodInterFace) {
        this.foods = foods;
        this.foodInterFace = foodInterFace;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.mylist,
                        parent,
                        false)

        );
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.bindFood(foods.get(position));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public ArrayList<Food> getSelectedFood(){
        ArrayList<Food> selectedFood = new ArrayList<>();
        for(Food food: foods){
            if(food.isSelect){
                selectedFood.add(food);
            }

        }
        return selectedFood;
    }


    class FoodViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layoutFood;
        View viewBackground;
        RoundedImageView imageFood;
        TextView textName, textPrice;
        ImageView imageSelected;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutFood = itemView.findViewById(R.id.item_layout);
            viewBackground =itemView.findViewById(R.id.viewBackground);
            imageFood = itemView.findViewById(R.id.imgFood);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            imageSelected = itemView.findViewById(R.id.imageSelected);

        }
        void bindFood(final Food food){
            imageFood.setImageResource(food.getImage());
            textName.setText(food.getName());
            textPrice.setText(Float.toString(food.getPrice()));
            if(food.isSelect){
                viewBackground.setBackgroundResource(R.drawable.food_selected_background);
                imageSelected.setVisibility(View.VISIBLE);
            }
            else{
                viewBackground.setBackgroundResource(R.drawable.food_backround);
                imageSelected.setVisibility(View.GONE);
            }
            layoutFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(food.isSelect){
                        viewBackground.setBackgroundResource(R.drawable.food_backround);
                        imageSelected.setVisibility(View.GONE);
                        food.isSelect = false;
                        if(getSelectedFood().size() ==0){
                            foodInterFace.onFoodAction(false);
                        }
                    } else {
                        viewBackground.setBackgroundResource(R.drawable.food_selected_background);
                        imageSelected.setVisibility(View.VISIBLE);
                        food.isSelect = true;
                        foodInterFace.onFoodAction(true);
                    }

                }
            });

        }
    }
}
