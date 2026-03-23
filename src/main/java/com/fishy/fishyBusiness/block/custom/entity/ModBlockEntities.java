package com.fishy.fishyBusiness.block.custom.entity;

import com.fishy.fishyBusiness.FishyBusiness;
import com.fishy.fishyBusiness.block.ModBlocks;
import com.fishy.fishyBusiness.block.custom.MarkerBlock;
import com.mojang.datafixers.types.Type;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public  static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FishyBusiness.MOD_ID);

public static final Supplier<BlockEntityType<MarkerBlockEntity>> MARKER_BE =
        BLOCK_ENTITIES.register("marker_be", ()-> BlockEntityType.Builder.of(
                MarkerBlockEntity::new, ModBlocks.MARKER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
