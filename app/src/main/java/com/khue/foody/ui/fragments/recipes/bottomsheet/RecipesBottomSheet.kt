package com.khue.foody.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.khue.foody.R
import com.khue.foody.databinding.RecipesBottomSheetBinding
import com.khue.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.khue.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.khue.foody.viewmodels.RecipesViewModel
import java.util.*


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel
    private var bottomSheetBinding: RecipesBottomSheetBinding? = null

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bottomSheetBinding = RecipesBottomSheetBinding.inflate(layoutInflater, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(
            viewLifecycleOwner,
            {
                value ->
                mealTypeChip = value.selectedMealType
                dietTypeChip = value.selectedMealType
                updateChip(value.selectedMealTypeId, bottomSheetBinding!!.mealTypeChipGroup)
                updateChip(value.selectedDietTypeId, bottomSheetBinding!!.dietTypeChipGroup)
            }
        )

        bottomSheetBinding!!.mealTypeChipGroup.setOnCheckedChangeListener{
            group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }

        bottomSheetBinding!!.dietTypeChipGroup.setOnCheckedChangeListener {
            group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = checkedId
        }

        bottomSheetBinding!!.applyBtn.setOnClickListener {
            recipesViewModel.saveMealAndDietTypeTemp(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return bottomSheetBinding!!.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                val targetView = chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
                chipGroup.requestChildFocus(targetView, targetView)
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomSheetBinding = null;
    }

}