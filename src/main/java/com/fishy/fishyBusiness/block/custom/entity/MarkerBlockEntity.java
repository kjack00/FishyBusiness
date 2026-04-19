package com.fishy.fishyBusiness.block.custom.entity;


import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MarkerBlockEntity extends BlockEntity{


    public MarkerBlockEntity( BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MARKER_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        int[] colors = new int[16*16];
        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 15; y++) {
            colors[y*16 + x] = this.nativeImage.getPixelRGBA(x, y);
            }
        }
        tag.putIntArray("colorlist", colors);

        System.out.println("saved: " + this);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if(tag.contains("colorlist")){
            int[] colors = tag.getIntArray("colorlist");
            for (int i = 0; i < colors.length; i++) {
                int x = i % 16;
                int y = i / 16;
                this.nativeImage.setPixelRGBA(x, y, colors[i]);
            }

        }
        System.out.println("loaded: " + this);
        this.updateTexture();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }


    private DynamicTexture dynamicTexture;
    private ResourceLocation textureLocation;
    private NativeImage nativeImage = new NativeImage(16, 16,true);


    public ResourceLocation getTextureLocation() {
        if (this.textureLocation == null) {
            this.dynamicTexture = new DynamicTexture(this.nativeImage);
            this.textureLocation = Minecraft.getInstance().getTextureManager().register("marker_tex_" + this.worldPosition.toShortString().replace(", ", "_"), this.dynamicTexture);
        }
        return this.textureLocation;
    }

    public void updateTexture() {
        if (this.dynamicTexture != null) {
            this.dynamicTexture.upload();
        }
    }

    public void drawPx(int x, int y, int col){
        this.nativeImage.setPixelRGBA(x, y, col);
        this.updateTexture();
        this.setChanged();
    }
}

