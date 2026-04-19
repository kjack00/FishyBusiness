package com.fishy.fishyBusiness.recipe;


import com.fishy.fishyBusiness.recipe.custom.RecolorMarkerRecipe;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class MarkerRecipeSerializer extends SimpleCraftingRecipeSerializer<RecolorMarkerRecipe> {

    public MarkerRecipeSerializer() {
        super(RecolorMarkerRecipe::new);
    }
}