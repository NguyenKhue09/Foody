package com.khue.foody.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.khue.foody.R
import com.khue.foody.databinding.IngredientsRowLayoutBinding
import com.khue.foody.models.ExtendedIngredient
import com.khue.foody.util.Constants.Companion.BASE_IMAGE_URL
import com.khue.foody.util.RecipesDiffUtil

class IngredientAdapter: RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {

    private var ingredientList = emptyList<ExtendedIngredient>()

    class MyViewHolder(val binding: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.ingredientImageView.load(BASE_IMAGE_URL + ingredientList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.binding.ingredientName.text = ingredientList[position].name.replaceFirstChar {
            it.uppercase()
        }
        holder.binding.ingredientAmount.text = ingredientList[position].amount.toString()
        holder.binding.ingredientUnit.text = ingredientList[position].unit
        holder.binding.ingredientOriginal.text = ingredientList[position].original
        holder.binding.ingredientConsistency.text = ingredientList[position].consistency


    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {

        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}