package com.fishy.fishyBusiness.block.custom.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MarkerBlockEntity extends BlockEntity {
    public MarkerBlockEntity( BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MARKER_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        //tag.put("posList", poslist.serialiseNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        //posList.deserialiseNBT(registries, tag.getCompound("posList"));
    }
}

