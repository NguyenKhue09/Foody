package com.khue.foody.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khue.foody.databinding.RecipesRowLayoutBinding
import com.khue.foody.models.FoodRecipe
import com.khue.foody.models.Result
import com.khue.foody.util.RecipesDiffUtil

class RecipesAdapter(): RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {


    private var recipes = emptyList<Result>()

    class MyViewHolder(private  val binding: RecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            // thay đổi layout nếu result change
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: FoodRecipe) {
        // thay cho notifyDataSetChanged()
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
        //notifyDataSetChanged()
    }

}