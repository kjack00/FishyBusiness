package com.fishy.fishyBusiness.recipe;


import com.fishy.fishyBusiness.FishyBusiness;
import com.fishy.fishyBusiness.recipe.custom.RecolorMarkerRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, FishyBusiness.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RecolorMarkerRecipe>> MARKER =
            SERIALIZERS.register("marker", MarkerRecipeSerializer::new);
}