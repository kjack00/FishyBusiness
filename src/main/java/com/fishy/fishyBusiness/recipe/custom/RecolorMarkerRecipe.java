package com.fishy.fishyBusiness.recipe.custom;

import com.fishy.fishyBusiness.components.ModDataComponents;
import com.fishy.fishyBusiness.item.ModItems;
import com.fishy.fishyBusiness.item.custom.MarkerItem;
import com.fishy.fishyBusiness.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

public class RecolorMarkerRecipe extends CustomRecipe {

    public RecolorMarkerRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int markerCount = 0;
        int dyeCount = 0;

        for (int i = 0; i < input.size(); i++){
            ItemStack itemStack = input.getItem(i);
            if(itemStack != ItemStack.EMPTY){
                if(itemStack.getItem() instanceof MarkerItem){
                    ++markerCount;
                } else if (itemStack.is(Tags.Items.DYES)){
                    ++dyeCount;
                }else {
                    return false;
                }
            }
        }
        return markerCount == 1 && dyeCount > 0;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack marker = ItemStack.EMPTY;
        List<DyeItem> dyes = new ArrayList<>();


        for (int i = 0; i < input.size(); i++){
            ItemStack itemStack = input.getItem(i);
            if(itemStack != ItemStack.EMPTY){
                if(itemStack.getItem() instanceof MarkerItem){
                    marker = itemStack;
                } else if (itemStack.is(Tags.Items.DYES)){
                    dyes.add((DyeItem) DyeItem.byId(DyeItem.getId(itemStack.getItem())));
                }
            }
        }

        ItemStack result = marker.copy();
        result = applyDyes(result, dyes);
        return result;

    }

    public static ItemStack applyDyes(ItemStack stack, List<DyeItem> dyes) {
        ItemStack itemstack = stack.copyWithCount(1);
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        Integer dyeditemcolor = itemstack.get(ModDataComponents.COLOR);

        if (dyeditemcolor == null) {
            dyeditemcolor = 0xFFFFFFFF;
        }
        int j1 = FastColor.ARGB32.red(dyeditemcolor);
        int k1 = FastColor.ARGB32.green(dyeditemcolor);
        int l1 = FastColor.ARGB32.blue(dyeditemcolor);
        l += Math.max(j1, Math.max(k1, l1));
        i += j1;
        j += k1;
        k += l1;
        i1++;


        for (DyeItem dyeitem : dyes) {
            int j3 = dyeitem.getDyeColor().getTextureDiffuseColor();
            int i2 = FastColor.ARGB32.red(j3);
            int j2 = FastColor.ARGB32.green(j3);
            int k2 = FastColor.ARGB32.blue(j3);
            l += Math.max(i2, Math.max(j2, k2));
            i += i2;
            j += j2;
            k += k2;
            i1++;
        }

        int l2 = i / i1;
        int i3 = j / i1;
        int k3 = k / i1;
        float f = (float)l / (float)i1;
        float f1 = (float)Math.max(l2, Math.max(i3, k3));
        l2 = (int)((float)l2 * f / f1);
        i3 = (int)((float)i3 * f / f1);
        k3 = (int)((float)k3 * f / f1);
        int l3 = FastColor.ARGB32.color(0, l2, i3, k3);
        boolean flag = dyeditemcolor == 0;

        int finalColor = FastColor.ARGB32.color(255, k3, i3, l2);
        itemstack.set(ModDataComponents.COLOR, finalColor);
        return itemstack;

    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MARKER.get();
    }


}
